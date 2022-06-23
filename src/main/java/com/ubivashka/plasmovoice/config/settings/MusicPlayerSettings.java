package com.ubivashka.plasmovoice.config.settings;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.configuration.ConfigurationHolder;
import com.ubivashka.configuration.annotations.ConfigField;
import com.ubivashka.plasmovoice.config.PluginConfig;

public class MusicPlayerSettings implements ConfigurationHolder {
    @ConfigField("sleep-delay")
    private int sleepDelay = 15;

    public MusicPlayerSettings(ConfigurationSection configurationSection) {
        PluginConfig.CONFIGURATION_PROCESSOR.resolve(configurationSection, this);
    }

    public MusicPlayerSettings() {
    }

    public int getSleepDelay() {
        return sleepDelay;
    }

}
