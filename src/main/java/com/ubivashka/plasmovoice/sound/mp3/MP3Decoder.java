package com.ubivashka.plasmovoice.sound.mp3;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.DecoderException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

public class MP3Decoder {
	private static final int READ_THRESHOLD = Integer.MAX_VALUE;
	private final Decoder decoder = new Decoder();;

	private final InputStream inputStream;
	private final Bitstream bitStream;

	private int sampleRate = -1, frameSize = -1, channels = -1, bitrate = -1;

	public MP3Decoder(InputStream inputStream) {
		this.inputStream = inputStream;
		this.bitStream = new Bitstream(inputStream);
	}

	public void init() throws BitstreamException {
		Header frame = bitStream.readFrame();
		bitStream.unreadFrame();
		if (frame == null)
			return;
		this.sampleRate = frame.frequency();
		this.frameSize = frame.framesize;
		this.bitrate = frame.bitrate_instant();
		this.channels = (frame.mode() == Header.SINGLE_CHANNEL) ? 1 : 2;
	}

	public MP3Data decode() throws BitstreamException, DecoderException {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

		Header frame;

		init();

		int framesReaded = 0;
		while (framesReaded++ <= READ_THRESHOLD && (frame = bitStream.readFrame()) != null) {
			SampleBuffer output = (SampleBuffer) decoder.decodeFrame(frame, bitStream);
			short[] pcm = output.getBuffer();

			for (short s : pcm) {
				arrayOutputStream.write(s & 0xff);
				arrayOutputStream.write((s >> 8) & 0xff);
			}
			bitStream.closeFrame();
		}
		return new MP3Data(arrayOutputStream.toByteArray(), this);
	}

	public static int getReadThreshold() {
		return READ_THRESHOLD;
	}

	public Decoder getDecoder() {
		return decoder;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public Bitstream getBitstream() {
		return bitStream;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public int getFrameSize() {
		return frameSize;
	}

	public int getBitrate() {
		return bitrate;
	}

	public int getChannels() {
		return channels;
	}

}
