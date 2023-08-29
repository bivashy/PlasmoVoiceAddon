package com.bivashy.plasmovoice.sound.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.sound.sampled.AudioInputStream;

public class AudioInputStreamReader {
    private final AudioInputStream audioInputStream;
    private Function<byte[], byte[]> converter;
    private Consumer<byte[]> listener;

    public AudioInputStreamReader(AudioInputStream audioInputStream) {
        this.audioInputStream = audioInputStream;
    }

    public AudioInputStreamReader withListener(Consumer<byte[]> listener) {
        this.listener = listener;
        return this;
    }

    public AudioInputStreamReader withConverter(Function<byte[], byte[]> converter) {
        if (this.converter != null) {
            this.converter = converter.compose(this.converter);
            return this;
        }
        this.converter = converter;
        return this;
    }

    public List<byte[]> read(int frameSize) throws IOException {
        List<byte[]> dataList = new ArrayList<>();
        int readedBytesCount = 0;
        byte[] wavSoundData = new byte[frameSize];
        while(readedBytesCount != -1) {
            readedBytesCount = audioInputStream.read(wavSoundData, 0, wavSoundData.length);
            if (readedBytesCount < 0)
                continue;
            byte[] processedData = converter == null ? wavSoundData : converter.apply(wavSoundData);
            dataList.add(processedData);
            if (listener != null)
                listener.accept(wavSoundData);
        }
        audioInputStream.close();
        return dataList;
    }
}
