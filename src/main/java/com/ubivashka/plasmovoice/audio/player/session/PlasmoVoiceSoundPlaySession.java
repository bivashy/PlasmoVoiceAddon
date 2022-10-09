package com.ubivashka.plasmovoice.audio.player.session;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ubivashka.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.frame.ISoundFrameProvider;
import com.ubivashka.plasmovoice.task.SelfCancellableScheduledTask;

import su.plo.voice.common.packets.Packet;
import su.plo.voice.common.packets.udp.PacketUDP;
import su.plo.voice.common.packets.udp.VoiceEndServerPacket;
import su.plo.voice.common.packets.udp.VoiceServerPacket;
import su.plo.voice.socket.SocketClientUDP;
import su.plo.voice.socket.SocketServerUDP;

public class PlasmoVoiceSoundPlaySession implements ISoundPlaySession {
    private final ISound sound;
    private final IPlayerAudioSource playerAudioSource;
    private final IPlasmoVoiceSoundController soundController;
    private final Player player;
    private boolean ended;

    public PlasmoVoiceSoundPlaySession(ISound sound, IPlayerAudioSource audioSource, IPlasmoVoiceSoundController soundController) {
        this.sound = sound;
        this.playerAudioSource = audioSource;
        this.soundController = soundController;
        this.player = Bukkit.getPlayer(playerAudioSource.getPlayerUniqueId());
    }

    public void playSound() {
        ISoundFrameProvider frameProvider = sound.getFrameProvider();

        new SelfCancellableScheduledTask() {
            int currentSequenceNumber = 0;
            int i = 0;

            @Override
            public void run() {
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
                VoiceServerPacket soundPacket = new VoiceServerPacket(data, playerAudioSource.getPlayerUniqueId(), currentSequenceNumber++,
                        (short) distance);

                try {
                    sendPacketToNearby(soundPacket, playerAudioSource);
                } catch(IOException e) {
                    e.printStackTrace();
                }

                i++;
            }

            private void end() {
                ended = true;
                cancel();
                if (player == null || !player.isOnline())
                    return;
                VoiceEndServerPacket endServerPacket = new VoiceEndServerPacket(playerAudioSource.getPlayerUniqueId());
                try {
                    sendPacketToNearby(endServerPacket, playerAudioSource);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }.scheduleWithFixedDelay(0, soundController.getMusicPlayerSettings().getSleepDelay(), TimeUnit.MILLISECONDS);
    }

    private void sendPacketToNearby(Packet soundPacket, IPlayerAudioSource playerAudioSource) throws IOException {
        int distance = soundController.getDistance();
        double distanceSquared = distance * distance * 1.25D;

        byte[] bytes = PacketUDP.write(soundPacket);

        for (Entry<Player, SocketClientUDP> entry : SocketServerUDP.clients.entrySet()) {
            Player receivePlayer = entry.getKey();
            SocketClientUDP clientUDP = entry.getValue();
            if (receivePlayer == null)
                return;
            if (!soundController.canHearSource() && receivePlayer.getUniqueId().equals(playerAudioSource.getPlayerUniqueId()))
                return;
            if (distanceSquared > 0.0D) {
                if (!playerAudioSource.getPlayerLocation().getWorld().getUID().equals(receivePlayer.getLocation().getWorld().getUID()))
                    return;
                if (playerAudioSource.getPlayerLocation().distanceSquared(receivePlayer.getLocation()) > distanceSquared)
                    return;
            }
            SocketServerUDP.sendTo(bytes, clientUDP);
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

    public IPlayerAudioSource getPlayerAudioSource() {
        return playerAudioSource;
    }

    public IPlasmoVoiceSoundController getSoundController() {
        return soundController;
    }

    public Player getPlayer() {
        return player;
    }
}
