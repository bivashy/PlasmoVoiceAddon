package com.ubivashka.plasmovoice.config.settings;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.annotations.ConfigField;
import com.ubivashka.plasmovoice.config.PluginConfig;

public class MusicPlayerSettings implements ConfigurationHolder {
    @ConfigField("sleep-delay")
    private int sleepDelay = 15;
    @ConfigField("volume")
    private int volume = 100;
    @ConfigField("bitrate")
    private int bitrate = 64000;
    @ConfigField("caching")
    private boolean caching = false;

    public MusicPlayerSettings(ConfigurationSection configurationSection) {
        PluginConfig.CONFIGURATION_PROCESSOR.resolve(configurationSection, this);
    }

    public MusicPlayerSettings() {
    }

    public int getVolume() {
        return volume;
    }

    public int getBitrate() {
        return bitrate;
    }

    public int getSleepDelay() {
        return sleepDelay;
    }

    public boolean isCaching() {
        return caching;
    }
}
