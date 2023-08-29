package com.bivashy.plasmovoice.opus;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.sun.jna.ptr.PointerByReference;
import com.bivashy.plasmovoice.sound.util.AudioUtils;

import de.maxhenkel.opus4j.Opus;

public class OpusEncoder {
    protected PointerByReference opusEncoder;
    protected int sampleRate;
    protected int frameSize;
    protected int maxPayloadSize;

    private boolean closed;

    public OpusEncoder(int sampleRate, int frameSize, int maxPayloadSize, int application, int bitrate) {
        this.sampleRate = sampleRate;
        this.frameSize = frameSize;
        this.maxPayloadSize = maxPayloadSize;
        IntBuffer error = IntBuffer.allocate(1);
        opusEncoder = Opus.INSTANCE.opus_encoder_create(sampleRate, 1, application, error);
        Opus.INSTANCE.opus_encoder_ctl(opusEncoder,Opus.OPUS_SET_SIGNAL_REQUEST,Opus.OPUS_SIGNAL_MUSIC);
        if (bitrate != -1)
            Opus.INSTANCE.opus_encoder_ctl(opusEncoder, Opus.OPUS_SET_BITRATE_REQUEST, bitrate);
        if (error.get() != Opus.OPUS_OK && opusEncoder == null) {
            throw new IllegalStateException("Opus encoder error " + error.get());
        }
    }

    public byte[] encode(byte[] rawAudio) {
        ShortBuffer nonEncodedBuffer = ShortBuffer.allocate(rawAudio.length / 2);
        ByteBuffer encoded = ByteBuffer.allocate(maxPayloadSize);
        for (int i = 0; i < rawAudio.length; i += 2) {
            short toShort = AudioUtils.bytesToShort(rawAudio[i], rawAudio[i + 1]);

            nonEncodedBuffer.put(toShort);
        }
        ((Buffer) nonEncodedBuffer).flip(); // java 8 support

        int result = Opus.INSTANCE.opus_encode(opusEncoder, nonEncodedBuffer, frameSize / 2, encoded,
                encoded.capacity());

        if (result < 0)
            throw new RuntimeException("Failed to encode audio data");

        byte[] audio = new byte[result];
        encoded.get(audio);
        return audio;
    }

    public void reset() {
        if (!this.closed)
            Opus.INSTANCE.opus_encoder_ctl(opusEncoder, Opus.OPUS_RESET_STATE);

    }

    public void close() {
        if (this.closed)
            return;

        this.closed = true;
        Opus.INSTANCE.opus_encoder_destroy(opusEncoder);
    }

    public boolean isClosed() {
        return closed;
    }

}