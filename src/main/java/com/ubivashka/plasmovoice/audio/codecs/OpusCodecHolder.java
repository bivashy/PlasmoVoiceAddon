package com.ubivashka.plasmovoice.audio.codecs;

import com.ubivashka.plasmovoice.function.MemoizingSupplier;
import com.ubivashka.plasmovoice.opus.OpusDecoder;
import com.ubivashka.plasmovoice.opus.OpusEncoder;
import de.maxhenkel.opus4j.Opus;

import javax.sound.sampled.AudioFormat;
import java.util.function.Supplier;

public class OpusCodecHolder extends AbstractCodecHolder {
    private static final int MTU_SIZE = 1024;
    private static final int JOPUS_MODE = Opus.OPUS_APPLICATION_VOIP;

    private Supplier<OpusEncoder> lazyEncoder = MemoizingSupplier.memoize(() -> new OpusEncoder(sampleRate, frameSize, MTU_SIZE, JOPUS_MODE));
    private Supplier<OpusDecoder> lazyDecoder = MemoizingSupplier.memoize(() -> new OpusDecoder(sampleRate, frameSize, MTU_SIZE));

    public OpusEncoder getEncoder() {
        return lazyEncoder.get();
    }

    public OpusDecoder getDecoder() {
        return lazyDecoder.get();
    }

    @Override
    public byte[] encode(byte[] data) {
        return getEncoder().encode(data);
    }

    @Override
    public byte[] decode(byte[] data) {
        return getDecoder().decode(data);
    }

    @Override
    public void resetEncoder() {
        getEncoder().reset();
    }

    @Override
    public void closeEncoder() {
        getEncoder().close();
    }

    public void resetDecoder() {
        getDecoder().reset();
    }

    @Override
    public void closeDecoder() {
		getDecoder().close();
    }

    @Override
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
        this.audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);
        this.frameSize = (sampleRate / 1000) * 2 * 20;

        lazyEncoder = MemoizingSupplier.memoize(() -> new OpusEncoder(sampleRate, frameSize, MTU_SIZE, JOPUS_MODE));
        lazyDecoder = MemoizingSupplier.memoize(() -> new OpusDecoder(sampleRate, frameSize, MTU_SIZE));
    }

}
