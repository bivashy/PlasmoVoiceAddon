package com.ubivashka.plasmovoice.config;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;

import net.md_5.bungee.api.ChatColor;

public class Messages {
	private HashMap<String, String> messages = new HashMap<>();
	private HashMap<String, Messages> subMessages = new HashMap<>();

	public Messages(ConfigurationSection section) {
		for (String key : section.getKeys(false)) {
			if (section.isConfigurationSection(key)) {
				addSubMessages(key, section);
				continue;
			}
			addMessage(key, section.getString(key));
		}
	}

	public String getMessage(String key) {
		return messages.get(key);
	}

	public Messages getSubMessages(String key) {
		return subMessages.get(key);
	}

	private void addSubMessages(String key, ConfigurationSection section) {
		subMessages.put(key, new Messages(section.getConfigurationSection(key)));
	}

	private void addMessage(String key, String message) {
		messages.put(key, color(message));
	}

	private String color(String text) {
		if (text == null)
			throw new IllegalArgumentException("Cannot color null text: " + text);
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}
