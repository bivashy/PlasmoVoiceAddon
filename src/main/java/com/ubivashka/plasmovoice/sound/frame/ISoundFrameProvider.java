package com.ubivashka.plasmovoice.sound.frame;

import java.util.List;

public interface ISoundFrameProvider {
    int getFramesCount();

    ISoundFrame getFrame(int frameIndex);

    static ISoundFrameProvider of(List<byte[]> dataList) {
        return new ISoundFrameProvider() {
            @Override
            public int getFramesCount() {
                return dataList.size();
            }

            @Override
            public ISoundFrame getFrame(int frameIndex) {
                return () -> dataList.get(frameIndex);
            }
        };
    }
}
