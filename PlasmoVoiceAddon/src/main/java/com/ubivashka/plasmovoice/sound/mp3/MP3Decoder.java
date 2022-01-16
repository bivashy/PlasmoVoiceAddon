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

	private final InputStream inputStream;

	public MP3Decoder(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public MP3Data decode() throws BitstreamException, DecoderException {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

		Bitstream bitStream = new Bitstream(inputStream);
		Decoder decoder = new Decoder();
		Header frame;

		Header firstHeader = bitStream.readFrame();

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

		return new MP3Data(arrayOutputStream.toByteArray(), firstHeader);
	}
}
