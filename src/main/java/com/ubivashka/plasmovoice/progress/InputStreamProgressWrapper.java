package com.ubivashka.plasmovoice.progress;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InputStreamProgressWrapper extends InputStream {
    private final List<ProgressListener> listeners = new ArrayList<>();
    private final long length;
    private InputStream in;
    private double percent;
    private int sumRead = 0;

    public InputStreamProgressWrapper(InputStream inputStream, long length) throws IOException {
        this.in = inputStream;
        this.length = length;
    }


    @Override
    public int read(byte[] b) throws IOException {
        int readCount = in.read(b);
        evaluatePercent(readCount);
        return readCount;
    }


    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int readCount = in.read(b, off, len);
        evaluatePercent(readCount);
        return readCount;
    }

    @Override
    public long skip(long n) throws IOException {
        long skip = in.skip(n);
        evaluatePercent(skip);
        return skip;
    }

    @Override
    public int read() throws IOException {
        int read = in.read();
        if (read != -1) {
            evaluatePercent(1);
        }
        return read;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        if (!super.markSupported())
            return;
        percent = 0;
        notifyListeners();
    }

    public InputStreamProgressWrapper addListener(ProgressListener listener) {
        this.listeners.add(listener);
        return this;
    }

    private void evaluatePercent(long readCount) {
        if (readCount != -1) {
            sumRead += readCount;
            percent = sumRead * 1.0 / length;
        }
        notifyListeners();
    }

    private void notifyListeners() {
        for (ProgressListener listener : listeners)
            listener.updateProgress((float) percent);
    }
}
