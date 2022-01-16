package com.ubivashka.plasmovoice.sound.mp3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import com.ubivashka.plasmovoice.audio.recorder.ISoundPlayer;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.pcm.AudioStreamSound;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;

/**
 * @author Ubivashka Данный класс конвертирует mp3 файл, либо ссылку на файл в
 *         pcm формат Для этого используется данная
 *         <a href="https://github.com/kevinstadler/JavaMP3">библиотека</a>
 */
public class MP3Sound implements ISound {

	private final AudioStreamSound convertedSound;

	public MP3Sound(InputStream musicInputStream, ISoundPlayer player) throws IOException {
		MP3Data mp3Data = null;
		try {
			mp3Data = new MP3Decoder(musicInputStream).decode();
		} catch (BitstreamException | DecoderException e) {
			e.printStackTrace();
			convertedSound = null;
			return;
		}

		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(mp3Data.getPcmData());
		int sampleRate = mp3Data.getSampleRate();
		int channels = mp3Data.getChannels();

		AudioFormat audioFormat = new AudioFormat(sampleRate, 16, channels, true, false);
		AudioInputStream audioInputStream = new AudioInputStream(arrayInputStream, audioFormat, Integer.MAX_VALUE);
		convertedSound = new AudioStreamSound(audioInputStream, player.getCodecHolder(), true);
	}

	@Override
	public List<byte[]> getDataList() {
		if (convertedSound == null)
			return Collections.emptyList();
		return convertedSound.getDataList();
	}
}
