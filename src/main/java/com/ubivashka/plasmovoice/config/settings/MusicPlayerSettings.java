package com.ubivashka.plasmovoice.config.settings;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.plasmovoice.config.PluginConfig;

public class MusicPlayerSettings implements ConfigurationHolder {
    @ConfigField("sleep-delay")
    private int sleepDelay = 15;
    @ConfigField("volume")
    private int volume = 100;
    @ConfigField("bitrate")
    private int bitrate = 64000;
    @ConfigField("distance")
    private int distance = 100;

    public MusicPlayerSettings(ConfigurationSection configurationSection) {
        PluginConfig.CONFIGURATION_PROCESSOR.resolve(configurationSection, this);
    }

    public MusicPlayerSettings(int sleepDelay, int volume, int bitrate, int distance) {
        this.sleepDelay = sleepDelay;
        this.volume = volume;
        this.bitrate = bitrate;
        this.distance = distance;
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

    public int getDistance() {
        return distance;
    }
}
