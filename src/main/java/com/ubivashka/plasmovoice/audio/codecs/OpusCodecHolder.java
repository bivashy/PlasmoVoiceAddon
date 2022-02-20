package com.ubivashka.plasmovoice.audio.codecs;

import javax.sound.sampled.AudioFormat;

import com.ubivashka.plasmovoice.opus.OpusDecoder;
import com.ubivashka.plasmovoice.opus.OpusEncoder;

import de.maxhenkel.opus4j.Opus;

public class OpusCodecHolder extends AbstractCodecHolder {
	private static final int MTU_SIZE = 1024;
	private static final int JOPUS_MODE = Opus.OPUS_APPLICATION_VOIP;

	private OpusEncoder encoder = new OpusEncoder(sampleRate, frameSize, MTU_SIZE, JOPUS_MODE);
	private OpusDecoder decoder = new OpusDecoder(sampleRate, frameSize, MTU_SIZE);

	public OpusEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(OpusEncoder encoder) {
		this.encoder = encoder;
	}

	public OpusDecoder getDecoder() {
		return decoder;
	}

	public void setDecoder(OpusDecoder decoder) {
		this.decoder = decoder;
	}

	@Override
	public byte[] encode(byte[] data) {
		return encoder.encode(data);
	}

	@Override
	public byte[] decode(byte[] data) {
		return decoder.decode(data);
	}

	@Override
	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
		this.audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);
		this.frameSize = (sampleRate / 1000) * 2 * 20;

		this.encoder = new OpusEncoder(sampleRate, frameSize, MTU_SIZE, JOPUS_MODE);
		this.decoder = new OpusDecoder(sampleRate, frameSize, MTU_SIZE);
	}

}
