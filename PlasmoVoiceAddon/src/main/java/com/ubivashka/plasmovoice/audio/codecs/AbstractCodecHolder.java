package com.ubivashka.plasmovoice.audio.codecs;

import javax.sound.sampled.AudioFormat;

public abstract class AbstractCodecHolder implements ICodecHolder {

	protected int sampleRate = 48000;
	protected int frameSize = (sampleRate / 1000) * 2 * 20;
	protected AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);

	@Override
	public int getSampleRate() {
		return sampleRate;
	}

	@Override
	public int getFrameSize() {
		return frameSize;
	}

	@Override
	public AudioFormat getAudioFormat() {
		return audioFormat;
	}

}
