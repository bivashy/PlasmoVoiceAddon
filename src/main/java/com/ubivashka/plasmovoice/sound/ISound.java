package com.ubivashka.plasmovoice.sound;

import java.util.List;

import com.ubivashka.plasmovoice.sound.frame.ISoundFrameProvider;

public interface ISound {
    ISoundFormat getSoundFormat();

    ISoundFrameProvider getFrameProvider();

    static ISound of(List<byte[]> dataList, ISoundFormat soundFormat) {
        return new ISound() {
            @Override
            public ISoundFormat getSoundFormat() {
                return soundFormat;
            }

            @Override
            public ISoundFrameProvider getFrameProvider() {
                return ISoundFrameProvider.of(dataList);
            }
        };
    }
}
