package com.ubivashka.plasmovoice.sound;

import java.util.List;

public class RawSound implements ISound{
	private List<byte[]> dataList;

	public RawSound(List<byte[]> dataList) {
		this.dataList = dataList;
	}

	@Override
	public List<byte[]> getDataList() {
		return dataList;
	}
}
