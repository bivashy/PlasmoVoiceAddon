package com.ubivashka.plasmovoice.audio.player.session;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.controller.PlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import su.plo.voice.common.packets.Packet;
import su.plo.voice.common.packets.udp.PacketUDP;
import su.plo.voice.common.packets.udp.VoiceEndServerPacket;
import su.plo.voice.common.packets.udp.VoiceServerPacket;
import su.plo.voice.socket.SocketClientUDP;
import su.plo.voice.socket.SocketServerUDP;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

public class PlasmoVoiceSoundPlaySession implements ISoundPlaySession {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    private final ISound sound;
    private final IPlayerAudioSource playerAudioSource;
    private final PlasmoVoiceSoundController soundController;
    private final Player player;

    public PlasmoVoiceSoundPlaySession(ISound sound, IPlayerAudioSource audioSource, PlasmoVoiceSoundController soundController) {
        this.sound = sound;
        this.playerAudioSource = audioSource;
        this.soundController = soundController;
        this.player = Bukkit.getPlayer(playerAudioSource.getPlayerUniqueId());

        playSound();
    }

    private void playSound() {
        int currentSequenceNumber = 0;
        List<byte[]> dataList = sound.getDataList();
        dataList.add(0, new byte[]{0, 0, 0}); // Add empty sound
        for (byte[] data : sound.getDataList()) {
            int distance = soundController.getDistance();
            if (!soundController.isPlaying())
                return;

            if (player == null || !player.isOnline()) {
                if (playerAudioSource.isTurnOffOnLeave())
                    return;
                continue;
            }
            VoiceServerPacket soundPacket = new VoiceServerPacket(data, playerAudioSource.getPlayerUniqueId(),
                    currentSequenceNumber++, (short) distance);

            try {
                sendPacketToNearby(soundPacket, playerAudioSource, player);
                Thread.sleep(soundController.getMusicPlayerSettings().getSleepDelay());
            } catch(InterruptedException |
                    IOException e) {
                e.printStackTrace();
            }
        }
        VoiceEndServerPacket endServerPacket = new VoiceEndServerPacket(playerAudioSource.getPlayerUniqueId());
        try {
            sendPacketToNearby(endServerPacket, playerAudioSource, player);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPacketToNearby(Packet soundPacket, IPlayerAudioSource playerAudioSource,
                                    Player player) throws IOException {
        int distance = soundController.getDistance();
        double distanceSquared = distance * distance * 1.25D;

        byte[] bytes = PacketUDP.write(soundPacket);

        for (Entry<Player, SocketClientUDP> entry : SocketServerUDP.clients.entrySet()) {
            Player receivePlayer = entry.getKey();
            SocketClientUDP clientUDP = entry.getValue();
            if (receivePlayer == null)
                return;
            if (!playerAudioSource.canHearSource()
                    && receivePlayer.getUniqueId().equals(playerAudioSource.getPlayerUniqueId()))
                return;
            if (distanceSquared > 0.0D) {
                if (!player.getLocation().getWorld().getUID().equals(receivePlayer.getLocation().getWorld().getUID()))
                    return;
                if (player.getLocation().distanceSquared(receivePlayer.getLocation()) > distanceSquared)
                    return;
            }
            SocketServerUDP.sendTo(bytes, clientUDP);
        }
    }
}
