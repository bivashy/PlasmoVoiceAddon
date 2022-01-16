package com.ubivashka.plasmovoice.config.settings;

import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.plasmovoice.config.processors.ConfigField;
import com.ubivashka.plasmovoice.config.processors.ConfigurationHolder;

public class MusicPlayerSettings extends ConfigurationHolder {
	@ConfigField(path = "sleep-delay")
	private int sleepDelay = 15;

	public MusicPlayerSettings(ConfigurationSection configurationSection) {
		super(configurationSection, false);
		setupFields();
	}

	public int getSleepDelay() {
		return sleepDelay;
	}

}
