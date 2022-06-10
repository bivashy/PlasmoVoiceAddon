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
    @ConfigField("music-player-settings.WAV")
    private MusicPlayerSettings wavMusicPlayerSettings = new MusicPlayerSettings();
    @ConfigField("music-player-settings.MP3")
    private MusicPlayerSettings mp3MusicPlayerSettings = new MusicPlayerSettings();
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

    public MusicPlayerSettings getWavMusicPlayerSettings() {
        return wavMusicPlayerSettings;
    }

    public MusicPlayerSettings getMp3MusicPlayerSettings() {
        return mp3MusicPlayerSettings;
    }
}
