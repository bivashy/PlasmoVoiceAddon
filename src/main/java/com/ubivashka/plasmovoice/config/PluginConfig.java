package com.ubivashka.plasmovoice.config;

import java.io.File;
import java.util.Optional;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import com.ubivashka.configuration.BukkitConfigurationProcessor;
import com.ubivashka.configuration.ConfigurationProcessor;
import com.ubivashka.configuration.annotation.ConfigField;
import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.config.bossbar.BossbarConfiguration;
import com.ubivashka.plasmovoice.config.resolver.OptionalConfigurationFieldResolver;
import com.ubivashka.plasmovoice.config.settings.CachingSettings;
import com.ubivashka.plasmovoice.config.settings.MusicCommandSettings;
import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;

public class PluginConfig {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    public static final ConfigurationProcessor CONFIGURATION_PROCESSOR = new BukkitConfigurationProcessor().registerFieldResolverFactory(Optional.class,
                    new OptionalConfigurationFieldResolver())
            .registerFieldResolver(File.class, context -> new File(
                    context.getString(PLUGIN.getDataFolder().getAbsolutePath()).replaceAll("%data_folder%", PLUGIN.getDataFolder().getAbsolutePath())));
    private final Configuration config;
    @ConfigField({"music-player-settings", "WAV"})
    private MusicPlayerSettings wavMusicPlayerSettings = new MusicPlayerSettings();
    @ConfigField({"music-player-settings", "MP3"})
    private MusicPlayerSettings mp3MusicPlayerSettings = new MusicPlayerSettings();
    @ConfigField("music-command-settings")
    private MusicCommandSettings musicCommandSettings = new MusicCommandSettings();
    @ConfigField("progress-boss-bar")
    private BossbarConfiguration bossbarConfiguration = new BossbarConfiguration();
    @ConfigField("caching")
    private CachingSettings cachingSettings = new CachingSettings();
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

    public MusicCommandSettings getMusicCommandSettings() {
        return musicCommandSettings;
    }

    public BossbarConfiguration getBossbarConfiguration() {
        return bossbarConfiguration;
    }

    public CachingSettings getCachingSettings() {
        return cachingSettings;
    }
}
