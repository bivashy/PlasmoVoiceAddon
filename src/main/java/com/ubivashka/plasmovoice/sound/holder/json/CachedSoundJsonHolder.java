package com.ubivashka.plasmovoice.sound.holder.json;

import java.io.File;
import java.net.URL;
import java.util.Optional;

import com.google.gson.Gson;
import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.sound.cache.CachedSound;

public class CachedSoundJsonHolder extends AbstractJsonHolder<CachedSound> {
    private static final String CACHE_DATA_FOLDER_NAME = "cached_sounds";

    public CachedSoundJsonHolder(PlasmoVoiceAddon plugin) {
        super(CachedSound.class, new File(plugin.getDataFolder(), CACHE_DATA_FOLDER_NAME), new Gson());
    }

    public Optional<CachedSound> findCachedSound(URL soundUrl) {
        String rawUrl = soundUrl.toString();
        return findFirstByPredicate(cachedSound -> cachedSound.getUrl().equals(rawUrl));
    }
}
