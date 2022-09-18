package com.ubivashka.plasmovoice.audio.codecs;

import javax.sound.sampled.AudioFormat;

public interface ICodecHolder {
    /**
     * @return sample rate.
     */
    int getSampleRate();

    /**
     * @param sampleRate - sample rate. Warning! This method may change framzeSize, and audioformat
     */
    void setSampleRate(int sampleRate);

    /**
     * @return frame size. Needed for SoundData
     */
    int getFrameSize();

    /**
     * @return audio format.
     */
    AudioFormat getAudioFormat();

    byte[] encode(byte[] data);

    byte[] decode(byte[] data);

    void resetEncoder();

    void resetDecoder();

    void closeEncoder();

    void closeDecoder();
}
