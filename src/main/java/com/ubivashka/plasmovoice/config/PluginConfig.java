package com.ubivashka.plasmovoice.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;

public class PluginConfig {
	private final Configuration config;
	private final MusicPlayerSettings musicPlayerSettings;
	private Messages messages;

	public PluginConfig(JavaPlugin plugin) {
		plugin.saveDefaultConfig();
		this.config = plugin.getConfig();

		this.musicPlayerSettings = new MusicPlayerSettings(
				getConfig().getConfigurationSection("music-player-settings"));

		if (config.contains("messages"))
			this.messages = new Messages(config.getConfigurationSection("messages"));
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
