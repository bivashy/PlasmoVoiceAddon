package com.ubivashka.plasmovoice.sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ubivashka.plasmovoice.sound.mp3.MP3Decoder;

import javazoom.jl.decoder.BitstreamException;

public enum SoundFormat {
	WAV {
		@Override
		public boolean isValid(InputStream stream) {
			try {
				AudioSystem.getAudioFileFormat(stream);
				return true;
			} catch (UnsupportedAudioFileException e) {
				return false;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	},
	MP3 {
		@Override
		public boolean isValid(InputStream stream) {
			MP3Decoder decoder = new MP3Decoder(stream);
			try {
				decoder.init();
			} catch (BitstreamException e) {
				return false;
			}
			return decoder.getBitrate() > 0;
		}
	};

	public abstract boolean isValid(InputStream stream);

	public static SoundFormat findMatchingSoundFormat(InputStream stream) {
		return Arrays.stream(values()).filter(soundFormat -> soundFormat.isValid(stream)).findFirst().orElse(null);
	}
}
