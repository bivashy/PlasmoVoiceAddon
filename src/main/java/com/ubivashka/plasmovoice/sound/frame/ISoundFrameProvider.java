package com.ubivashka.plasmovoice.sound.frame;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ISoundFrameProvider {
    ISoundFrame EMPTY_FRAME = () -> new byte[0];

    long getFramesCount();

    ISoundFrame getFrame(int frameIndex);

    CompletableFuture<List<byte[]>> getAllFrames();

    static ISoundFrameProvider of(List<byte[]> dataList) {
        return new ISoundFrameProvider() {
            @Override
            public long getFramesCount() {
                return dataList.size();
            }

            @Override
            public ISoundFrame getFrame(int frameIndex) {
                return () -> dataList.get(frameIndex);
            }

            @Override
            public CompletableFuture<List<byte[]>> getAllFrames() {
                return CompletableFuture.completedFuture(dataList);
            }
        };
    }
}
