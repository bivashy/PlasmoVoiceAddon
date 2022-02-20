package com.ubivashka.plasmovoice.audio.codecs;

import javax.sound.sampled.AudioFormat;

public interface ICodecHolder {
	/**
	 * @return Частота дискретизации. Простыми словами диапозон звука.
	 */
	public int getSampleRate();

	/**
	 * @param sampleRate - Частота дискретизации. Внимание! Данный метод меняет и
	 *                   другие значения такие как framzeSize, и audioformat
	 */
	public void setSampleRate(int sampleRate);

	/**
	 * @return Возвращает размер аудиокадра. Требуется для создания SoundData
	 */
	public int getFrameSize();

	/**
	 * @return Возвращает формат аудио.
	 */
	public AudioFormat getAudioFormat();

	public byte[] encode(byte[] data);

	public byte[] decode(byte[] data);
}
