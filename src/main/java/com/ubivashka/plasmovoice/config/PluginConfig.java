package com.ubivashka.plasmovoice.config;

import com.ubivashka.configuration.BukkitConfigurationProcessor;
import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotations.ConfigField;
import com.ubivashka.plasmovoice.config.factories.ConfigurationHolderResolverFactory;
import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginConfig {
    public static final ConfigurationProcessor CONFIGURATION_PROCESSOR = new BukkitConfigurationProcessor().registerFieldResolverFactory(ConfigurationHolder.class, new ConfigurationHolderResolverFactory());
    private final Configuration config;
    @ConfigField("music-player-settings")
    private MusicPlayerSettings musicPlayerSettings = new MusicPlayerSettings();
    @ConfigField("messages")
    private Messages messages = new Messages();

    public PluginConfig(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();

        CONFIGURATION_PROCESSOR.resolve(config, this);
    }

    public Configuration getConfig() {
        return config;
    }

    public Messages getMessages() {
        return messages;
    }

    public MusicPlayerSettings getMusicPlayerSettings() {
        return musicPlayerSettings;
    }

}
