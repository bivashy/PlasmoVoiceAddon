package com.ubivashka.plasmovoice.sound.holder.json;

import java.io.File;

import com.google.gson.Gson;
import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.sound.cache.CachedSound;

public class CachedSoundJsonHolder extends AbstractJsonHolder<CachedSound> {
    private static final String FOLDER_NAME = "cached_sounds";

    public CachedSoundJsonHolder(PlasmoVoiceAddon plugin) {
        super(CachedSound.class, new File(plugin.getDataFolder() + File.separator + FOLDER_NAME), new Gson());
    }
}
