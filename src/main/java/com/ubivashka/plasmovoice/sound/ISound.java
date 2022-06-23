package com.ubivashka.plasmovoice.sound;

import java.util.List;

public interface ISound {
    ISoundFormat getSoundFormat();

    List<byte[]> getDataList();

    static ISound of(List<byte[]> dataList, ISoundFormat soundFormat) {
        return new ISound() {
            @Override
            public ISoundFormat getSoundFormat() {
                return soundFormat;
            }

            @Override
            public List<byte[]> getDataList() {
                return dataList;
            }
        };
    }
}
