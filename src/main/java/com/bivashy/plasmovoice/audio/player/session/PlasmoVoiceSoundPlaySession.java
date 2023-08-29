package com.bivashy.plasmovoice.audio.player.session;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.bivashy.plasmovoice.sound.frame.ISoundFrameProvider;
import com.bivashy.plasmovoice.task.SelfCancellableScheduledTask;
import com.bivashy.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.bivashy.plasmovoice.event.SoundSessionEndEvent;
import com.bivashy.plasmovoice.sound.ISound;

import su.plo.voice.common.packets.Packet;
import su.plo.voice.common.packets.udp.PacketUDP;
import su.plo.voice.common.packets.udp.VoiceEndServerPacket;
import su.plo.voice.common.packets.udp.VoiceServerPacket;
import su.plo.voice.socket.SocketClientUDP;
import su.plo.voice.socket.SocketServerUDP;

public class PlasmoVoiceSoundPlaySession implements ISoundPlaySession {
    private final ISound sound;
    private final PlasmoVoiceSoundPlayer soundPlayer;
    private final IPlasmoVoiceSoundController soundController;
    private final Player player;
    private SelfCancellableScheduledTask task;
    private boolean ended;
    private boolean paused;

    public PlasmoVoiceSoundPlaySession(ISound sound, PlasmoVoiceSoundPlayer soundPlayer, IPlasmoVoiceSoundController soundController) {
        this.sound = sound;
        this.soundPlayer = soundPlayer;
        this.soundController = soundController;
        this.player = Bukkit.getPlayer(soundPlayer.getSource().getPlayerUniqueId());
    }

    public void playSound() {
        ISoundFrameProvider frameProvider = sound.getFrameProvider();

        task = new SelfCancellableScheduledTask() {
            int currentSequenceNumber = 0;
            int i = 0;

            @Override
            public void run() {
                if (paused)
                    return;
                if (!(i < frameProvider.getFramesCount())) {
                    end();
                    return;
                }
                if (ended)
                    return;

                byte[] data = frameProvider.getFrame(i).getData();
                if (data.length == 0)
                    return;
                int distance = soundController.getDistance();
                if (!soundController.isPlaying()) {
                    end();
                    return;
                }
                if ((player == null || !player.isOnline()) && soundController.isTurnOffOnLeave()) {
                    end();
                    return;
                }
                VoiceServerPacket soundPacket = new VoiceServerPacket(data, soundPlayer.getSource().getPlayerUniqueId(), currentSequenceNumber++,
                        (short) distance);

                try {
                    sendPacketToNearby(soundPacket);
                } catch(IOException e) {
                    e.printStackTrace();
                }

                i++;
            }
        };
        task.scheduleWithFixedDelay(0, soundController.getMusicPlayerSettings().getSleepDelay(), TimeUnit.MILLISECONDS);
    }

    private void sendPacketToNearby(Packet soundPacket) throws IOException {
        int distance = soundController.getDistance();
        double distanceSquared = distance * distance * 1.25D;

        byte[] bytes = PacketUDP.write(soundPacket);

        for (Entry<Player, SocketClientUDP> entry : SocketServerUDP.clients.entrySet()) {
            Player receivePlayer = entry.getKey();
            SocketClientUDP clientUDP = entry.getValue();
            if (receivePlayer == null)
                return;
            if (!soundController.canHearSource() && receivePlayer.getUniqueId().equals(soundPlayer.getSource().getPlayerUniqueId()))
                return;
            if (distanceSquared > 0.0D) {
                if (!soundPlayer.getSource().getPlayerLocation().getWorld().getUID().equals(receivePlayer.getLocation().getWorld().getUID()))
                    return;
                if (soundPlayer.getSource().getPlayerLocation().distanceSquared(receivePlayer.getLocation()) > distanceSquared)
                    return;
            }
            SocketServerUDP.sendTo(bytes, clientUDP);
        }
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void continuePlaying() {
        paused = false;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void end() {
        ended = true;
        task.cancel();
        Bukkit.getScheduler()
                .runTask(PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class), () -> Bukkit.getPluginManager().callEvent(new SoundSessionEndEvent(this)));
        if (player == null || !player.isOnline())
            return;
        VoiceEndServerPacket endServerPacket = new VoiceEndServerPacket(soundPlayer.getSource().getPlayerUniqueId());
        try {
            sendPacketToNearby(endServerPacket);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEnded() {
        return ended;
    }

    @Override
    public ISound getSound() {
        return sound;
    }

    public PlasmoVoiceSoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public IPlasmoVoiceSoundController getSoundController() {
        return soundController;
    }

    public Player getPlayer() {
        return player;
    }
}
