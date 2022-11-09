package com.ubivashka.plasmovoice.sound.cache;

import com.google.gson.annotations.SerializedName;

public class CachedSound {
    @SerializedName("url")
    private String url;
    @SerializedName("format")
    private String soundFormat;
    @SerializedName("file")
    private String cachedFile;

    public CachedSound(String url, String soundFormat, String cachedFile) {
        this.url = url;
        this.soundFormat = soundFormat;
        this.cachedFile = cachedFile;
    }

    public String getUrl() {
        return url;
    }

    public String getSoundFormat() {
        return soundFormat;
    }

    public String getCachedFile() {
        return cachedFile;
    }

    @Override
    public String toString() {
        return cachedFile;
    }
}
