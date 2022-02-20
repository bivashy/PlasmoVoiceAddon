package com.ubivashka.plasmovoice.sound.pcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.ubivashka.plasmovoice.audio.codecs.ICodecHolder;
import com.ubivashka.plasmovoice.audio.recorder.ISoundPlayer;
import com.ubivashka.plasmovoice.sound.ISound;

public class AudioStreamSound implements ISound {
	private List<byte[]> dataList;

	/**
	 * 
	 * @param audioInputStream - Музыка которую нужно конвертировать. Для того чтобы
	 *                         получить из файла или InputStream используйте (Можно
	 *                         и другим способом):
	 *                         {@link javax.sound.sampled.AudioSystem}
	 * @param soundPlayer      - Используется для того чтобы сжать PCM с помощью
	 *                         ICodecHolder
	 */
	public AudioStreamSound(AudioInputStream audioInputStream, ISoundPlayer soundPlayer) {
		this(audioInputStream, soundPlayer.getCodecHolder(), false);
	}

	/**
	 * 
	 * @param audioInputStream - Музыка которую нужно конвертировать. Для того чтобы
	 *                         получить из файла или InputStream используйте (Можно
	 *                         и другим способом):
	 *                         {@link javax.sound.sampled.AudioSystem}
	 * @param soundPlayer      - Используется для сжатия звука
	 * @param convert          - Решает конвертировать ли формат звука
	 */
	public AudioStreamSound(AudioInputStream audioInputStream, ICodecHolder codecHolder, boolean convert) {
		List<byte[]> newDataList = new ArrayList<>();
		AudioInputStream newAudioInputStream = audioInputStream;
		if (convert)
			newAudioInputStream = AudioSystem.getAudioInputStream(codecHolder.getAudioFormat(), audioInputStream);

		try {
			new AudioInputStreamReader(newAudioInputStream, (data) -> newDataList.add(codecHolder.encode(data)))
					.read(codecHolder.getFrameSize());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.dataList = newDataList;
	}

	@Override
	public List<byte[]> getDataList() {
		return dataList;
	}

}