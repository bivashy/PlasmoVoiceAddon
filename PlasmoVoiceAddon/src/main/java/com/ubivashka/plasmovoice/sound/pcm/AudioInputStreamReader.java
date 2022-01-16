package com.ubivashka.plasmovoice.sound.pcm;

import java.io.IOException;
import java.util.function.Consumer;

import javax.sound.sampled.AudioInputStream;

public class AudioInputStreamReader {
	private final AudioInputStream audioInputStream;
	private final Consumer<byte[]> audioDataConsumer;

	public AudioInputStreamReader(AudioInputStream audioInputStream, Consumer<byte[]> audioDataConsumer) {
		this.audioInputStream = audioInputStream;
		this.audioDataConsumer = audioDataConsumer;
	}

	public void read(int frameSize) throws IOException {
		int readedBytesCount = 0;
		byte[] wavSoundData = new byte[frameSize];
		while (readedBytesCount != -1) {
			readedBytesCount = audioInputStream.read(wavSoundData, 0, wavSoundData.length);

			if (readedBytesCount >= 0)
				audioDataConsumer.accept(wavSoundData);
		}
		audioInputStream.close();
	}
}
