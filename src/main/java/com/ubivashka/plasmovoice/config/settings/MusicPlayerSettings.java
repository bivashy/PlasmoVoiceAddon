package com.ubivashka.plasmovoice.config.settings;

import com.ubivashka.configuration.annotations.ConfigField;
import com.ubivashka.plasmovoice.config.ConfigurationHolder;
import com.ubivashka.plasmovoice.config.PluginConfig;
import org.bukkit.configuration.ConfigurationSection;

public class MusicPlayerSettings implements ConfigurationHolder {
    @ConfigField("sleep-delay")
    private int sleepDelay = 15;

    public MusicPlayerSettings(ConfigurationSection configurationSection) {
        PluginConfig.CONFIGURATION_PROCESSOR.resolve(configurationSection,this);
    }

    public MusicPlayerSettings() {
    }

    public int getSleepDelay() {
        return sleepDelay;
    }

}
