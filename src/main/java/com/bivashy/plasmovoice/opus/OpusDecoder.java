package com.bivashy.plasmovoice.opus;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.sun.jna.ptr.PointerByReference;
import com.bivashy.plasmovoice.sound.util.AudioUtils;

import de.maxhenkel.opus4j.Opus;

public class OpusDecoder {
    protected PointerByReference opusDecoder;
    protected boolean closed;
    protected int sampleRate;
    protected int frameSize;
    protected int maxPayloadSize;

    public OpusDecoder(int sampleRate, int frameSize, int maxPayloadSize) {
        this.sampleRate = sampleRate;
        this.frameSize = frameSize;
        this.maxPayloadSize = maxPayloadSize;
        IntBuffer error = IntBuffer.allocate(1);
        opusDecoder = Opus.INSTANCE.opus_decoder_create(sampleRate, 1, error);
        if (error.get() != Opus.OPUS_OK && opusDecoder == null) {
            throw new IllegalStateException("Opus decoder error " + error.get());
        }
    }

    public byte[] decode(byte[] data) {
        if (this.closed) {
            return new byte[0];
        }

        int result;
        ShortBuffer decoded = ShortBuffer.allocate(4096);
        if (data == null || data.length == 0) {
            result = Opus.INSTANCE.opus_decode(opusDecoder, null, 0, decoded, frameSize / 2, 0);
        } else {
            result = Opus.INSTANCE.opus_decode(opusDecoder, data, data.length, decoded, frameSize / 2, 0);
        }

        if (result < 0) {
            // throw new RuntimeException("Failed to decode audio data");
            return new byte[0];
        }

        short[] audio = new short[result];
        decoded.get(audio);

        byte[] outData = new byte[audio.length * 2];

        for (int i = 0; i < audio.length; i++) {
            byte[] split = AudioUtils.shortToBytes(audio[i]);
            outData[i * 2] = split[0];
            outData[i * 2 + 1] = split[1];
        }
        return outData;
    }

    public void reset() {
        if (!this.closed) {
            Opus.INSTANCE.opus_decoder_ctl(opusDecoder, Opus.OPUS_RESET_STATE);
        }
    }

    public void close() {
        if (this.closed) {
            return;
        }

        this.closed = true;
        Opus.INSTANCE.opus_decoder_destroy(opusDecoder);
    }
}