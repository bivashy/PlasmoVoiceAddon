package com.ubivashka.plasmovoice.config.processors;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;

public enum ImportantAction {
	NOTHING {
		@Override
		public void doAction(String fieldPath, ConfigurationSection configurationSection) {
		}
	},
	LOG {
		@Override
		public void doAction(String fieldPath, ConfigurationSection configurationSection) {
			Bukkit.getServer().getLogger().log(Level.SEVERE,
					"The important field: " + fieldPath + " not finded in section: " + configurationSection);
		}
	},
	LOG_DISABLE_PLUGIN {
		@Override
		public void doAction(String fieldPath, ConfigurationSection configurationSection) {
			ImportantAction.LOG.doAction(fieldPath, configurationSection);
			Bukkit.getPluginManager().disablePlugin(PLUGIN);
		}
	};

	private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);

	public abstract void doAction(String fieldPath, ConfigurationSection configurationSection);
}
