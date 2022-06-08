package com.ubivashka.plasmovoice.audio.codecs;

import javax.sound.sampled.AudioFormat;

public interface ICodecHolder {
    /**
     * @return Частота дискретизации. Простыми словами диапозон звука.
     */
    int getSampleRate();

    /**
     * @param sampleRate - Частота дискретизации. Внимание! Данный метод меняет и
     *                   другие значения такие как framzeSize, и audioformat
     */
    void setSampleRate(int sampleRate);

    /**
     * @return Возвращает размер аудиокадра. Требуется для создания SoundData
     */
    int getFrameSize();

    /**
     * @return Возвращает формат аудио.
     */
    AudioFormat getAudioFormat();

    byte[] encode(byte[] data);

    byte[] decode(byte[] data);
}
