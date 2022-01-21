package com.ubivashka.plasmovoice.sound;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.sound.mp3.MP3Sound;
import com.ubivashka.plasmovoice.sound.pcm.AudioStreamSound;

public class SoundBuilder {
	private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
	
	private final InputStream inputStream;
	private SoundFormat soundFormat;

	public SoundBuilder(InputStream inputStream) {
		this.inputStream = inputStream;
		this.soundFormat = SoundFormat.findMatchingSoundFormat(inputStream);
	}

	public SoundBuilder withSoundFormat(SoundFormat soundFormat) {
		this.soundFormat = soundFormat;
		return this;
	}

	public ISound build() throws UnsupportedAudioFileException, IOException {
		ISound sound = null;
		
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		switch (soundFormat) {
		case WAV:
			AudioInputStream audioInputStream;
			
				audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
			
			sound = new AudioStreamSound(audioInputStream, PLUGIN.getPlasmoVoiceSoundPlayer().getCodecHolder(), true);
			break;
		case MP3:
			
				sound = new MP3Sound(bufferedInputStream, PLUGIN.getPlasmoVoiceSoundPlayer());
			
			break;
		}
		
		return sound;
	}
}
