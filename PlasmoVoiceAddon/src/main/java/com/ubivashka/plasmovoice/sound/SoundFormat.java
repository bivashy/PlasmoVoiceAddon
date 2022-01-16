package com.ubivashka.plasmovoice.sound;

import java.util.Arrays;

public enum SoundFormat {
	MP3("mp3"), WAV("wav");

	private final String fileExtension;

	private SoundFormat(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public boolean isFormatMatch(String fileName) {
		return fileName.equalsIgnoreCase(fileExtension);
	}

	public static SoundFormat findMatchingSoundFormat(String fileName) {
		return Arrays.stream(values()).filter(soundFormat -> soundFormat.isFormatMatch(fileName)).findFirst()
				.orElse(null);
	}
}
