package com.ubivashka.plasmovoice.sound;

import java.util.List;

public interface ISound {
	SoundFormat getSoundFormat();
	List<byte[]> getDataList();
}
