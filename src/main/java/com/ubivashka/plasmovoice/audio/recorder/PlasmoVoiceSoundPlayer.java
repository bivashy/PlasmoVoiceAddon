package com.ubivashka.plasmovoice.audio.recorder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ubivashka.plasmovoice.audio.codecs.ICodecHolder;
import com.ubivashka.plasmovoice.audio.codecs.OpusCodecHolder;
import com.ubivashka.plasmovoice.audio.sources.IAudioSource;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;

import su.plo.voice.PlasmoVoice;

public class PlasmoVoiceSoundPlayer implements ISoundPlayer {
	private final OpusCodecHolder opusCodecHolder = new OpusCodecHolder();

	@Override
	public ISoundPlaySession playSound(ISound sound, int distance, IAudioSource audioSource) {
		if (!(audioSource instanceof IPlayerAudioSource))
			return null;
		updateCodecHolder();
		IPlayerAudioSource playerAudioSource = (IPlayerAudioSource) audioSource;
		Player player = Bukkit.getPlayer(playerAudioSource.getPlayerUniqueId());
		if (player == null)
			return null;

		return new PlasmoVoiceSoundPlaySession(sound, playerAudioSource, distance);
	}

	private void updateCodecHolder() {
		opusCodecHolder.setSampleRate(PlasmoVoice.getInstance().getVoiceConfig().getSampleRate());
	}

	@Override
	public ICodecHolder getCodecHolder() {
		return opusCodecHolder;
	}

}
