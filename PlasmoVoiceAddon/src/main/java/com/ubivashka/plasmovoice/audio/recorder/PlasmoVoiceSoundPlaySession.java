package com.ubivashka.plasmovoice.audio.recorder;

import java.io.IOException;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;

import su.plo.voice.common.packets.Packet;
import su.plo.voice.common.packets.udp.PacketUDP;
import su.plo.voice.common.packets.udp.VoiceEndServerPacket;
import su.plo.voice.common.packets.udp.VoiceServerPacket;
import su.plo.voice.socket.SocketClientUDP;
import su.plo.voice.socket.SocketServerUDP;

public class PlasmoVoiceSoundPlaySession implements ISoundPlaySession {
	private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
	private int sleepDelay = PLUGIN.getPluginConfig().getMusicPlayerSettings().getSleepDelay();
	private final ISound sound;
	private final IPlayerAudioSource playerAudioSource;
	private final int distance;
	private final Player player;

	private boolean cancelled = false;

	public PlasmoVoiceSoundPlaySession(ISound sound, IPlayerAudioSource audioSource, int distance) {
		this.sound = sound;
		this.playerAudioSource = audioSource;
		this.distance = distance;
		this.player = Bukkit.getPlayer(playerAudioSource.getPlayerUniqueId());

		playSound();
	}

	private void playSound() {
		int currentSequenceNumber = 0;
		byte[] firstData = { 0 };
		boolean firstDataSended = false;
		for (byte[] data : sound.getDataList()) {

			if (cancelled)
				return;

			if (player == null || !player.isOnline()) {

				if (playerAudioSource.isTurnOffOnLeave())
					return;
				continue;
			}
			if (!firstDataSended) {
				data = firstData;
				firstDataSended = true;
			}
			VoiceServerPacket soundPacket = new VoiceServerPacket(data, playerAudioSource.getPlayerUniqueId(),
					currentSequenceNumber++, (short) distance);

			try {
				sendPacketToNearby(soundPacket, playerAudioSource, distance, player);
				Thread.sleep(sleepDelay);
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
		VoiceEndServerPacket endServerPacket = new VoiceEndServerPacket(playerAudioSource.getPlayerUniqueId());
		try {
			sendPacketToNearby(endServerPacket, playerAudioSource, distance, player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendPacketToNearby(Packet soundPacket, IPlayerAudioSource playerAudioSource, int distance,
			Player player) throws IOException {
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

	@Override
	public void cancelSession() {
		cancelled = true;
	}

	public int getSleepDelay() {
		return sleepDelay;
	}

	public void setSleepDelay(int sleepDelay) {
		this.sleepDelay = sleepDelay;
	}

}
