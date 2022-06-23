package com.ubivashka.plasmovoice.sound.cache;

import com.google.gson.annotations.SerializedName;

public class CachedSound {
    @SerializedName("url")
    private String url;
    @SerializedName("file")
    private String cachedFile;

    public CachedSound(String url, String cachedFile) {
        this.url = url;
        this.cachedFile = cachedFile;
    }

    public String getUrl() {
        return url;
    }

    public String getCachedFile() {
        return cachedFile;
    }
}
