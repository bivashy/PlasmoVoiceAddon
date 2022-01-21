package com.ubivashka.plasmovoice.sound.mp3;

public class MP3Data {
	private final byte[] pcmData;
	private final int sampleRate, channels, frameSize, bitrate;

	public MP3Data(byte[] pcmData, MP3Decoder decoder) {
		this.pcmData = pcmData;
		this.sampleRate = decoder.getSampleRate();
		this.frameSize = decoder.getFrameSize();
		this.channels = decoder.getChannels();
		this.bitrate = decoder.getBitrate();
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

	public int getFrameSize() {
		return frameSize;
	}

	public int getBitrate() {
		return bitrate;
	}

}
