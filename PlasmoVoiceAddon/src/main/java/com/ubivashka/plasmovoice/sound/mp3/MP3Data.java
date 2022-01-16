package com.ubivashka.plasmovoice.sound.mp3;

import javazoom.jl.decoder.Header;

public class MP3Data {
	private final byte[] pcmData;
	private final int sampleRate;
	private final int channels;

	public MP3Data(byte[] pcmData, Header header) {
		this.pcmData = pcmData;
		this.sampleRate = header.frequency();
		this.channels = (header.mode()==Header.SINGLE_CHANNEL) ? 1 : 2;
	}

	public byte[] getPcmData() {
		return pcmData;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public int getChannels() {
		return channels;
	}

}
