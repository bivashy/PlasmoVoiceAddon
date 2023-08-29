package com.bivashy.plasmovoice.config.settings;

import java.io.File;

import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.config.PluginConfig;
import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.configuration.holder.ConfigurationSectionHolder;

public class CachingSettings implements ConfigurationHolder {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    @ConfigField("size-limit")
    private long sizeLimit = -1;
    @ConfigField("block-undefined-size")
    private boolean blockUndefinedSize = false;
    @ConfigField("cache-folder")
    private File cacheFolder = new File(PLUGIN.getDataFolder(), "caches");

    public CachingSettings(ConfigurationSectionHolder sectionHolder) {
        PluginConfig.CONFIGURATION_PROCESSOR.resolve(sectionHolder, this);
    }

    public CachingSettings() {
    }

    public long getSizeLimit() {
        return sizeLimit;
    }

    public boolean isBlockUndefinedSize() {
        return blockUndefinedSize;
    }

    public File getCacheFolder() {
        return cacheFolder;
    }
}
