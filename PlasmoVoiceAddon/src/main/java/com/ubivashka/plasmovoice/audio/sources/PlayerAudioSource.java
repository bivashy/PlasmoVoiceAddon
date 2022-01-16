package com.ubivashka.plasmovoice.audio.sources;

import java.util.UUID;

import com.ubivashka.plasmovoice.audio.recorder.ISoundPlayer;
import com.ubivashka.plasmovoice.sound.ISound;

public class PlayerAudioSource extends AbstractPlayerAudioSource {
	private final UUID playerUniqueId;

	public PlayerAudioSource(UUID playerUniqueId, ISoundPlayer soundPlayer) {
		super(soundPlayer);
		this.playerUniqueId = playerUniqueId;
	}

	@Override
	public UUID getPlayerUniqueId() {
		return playerUniqueId;
	}

	@Override
	public void sendAudioData(ISound sound, int distance) {
		soundPlayer.playSound(sound, distance, this);
	}

	
}
