package com.ubivashka.plasmovoice.sound.holder.json;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import com.google.gson.Gson;
import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.sound.cache.CachedSound;

public class CachedSoundJsonHolder extends AbstractJsonHolder<CachedSound> {
    private static final String CACHE_DATA_FOLDER_NAME = "cached_sounds";
    private static final String CACHE_FOLDER_NAME = "caches";
    private final File cacheDirectory;
    private final PlasmoVoiceAddon plugin;

    public CachedSoundJsonHolder(PlasmoVoiceAddon plugin) {
        super(CachedSound.class, new File(plugin.getDataFolder() + File.separator + CACHE_DATA_FOLDER_NAME), new Gson());
        this.cacheDirectory = new File(plugin.getDataFolder() + File.separator + CACHE_FOLDER_NAME);
        this.plugin = plugin;

        this.cacheDirectory.mkdirs();
    }

    public InputStream createCachedInputStream(URL url, InputStream connectionStream, boolean forceCache) throws IOException {
        Optional<CachedSound> cachedSound =
                plugin.getCachedSoundHolder().findFirstByPredicate(sound -> sound.getUrl().equals(url.toString()));
        Optional<Path> cachedSoundPath = cachedSound.map(sound -> Paths.get(sound.getCachedFile()));

        if (!cachedSound.isPresent() || forceCache) {
            Path musicPath = cachedSoundPath.orElse(File.createTempFile("music", "", cacheDirectory).toPath());
            Files.copy(connectionStream, musicPath, StandardCopyOption.REPLACE_EXISTING);
            connectionStream.close();

            plugin.getCachedSoundHolder().add(new CachedSound(url.toString(), musicPath.toString()));
            return new BufferedInputStream(new FileInputStream(musicPath.toFile()));
        } else {
            Path path = Paths.get(cachedSound.get().getCachedFile());
            if (!Files.exists(path))
                return connectionStream;
            connectionStream.close();
            return new BufferedInputStream(new FileInputStream(cachedSoundPath.get().toFile()));
        }
    }
}
