package com.ubivashka.plasmovoice;

import com.ubivashka.plasmovoice.audio.recorder.PlasmoVoiceSoundPlayer;
import com.ubivashka.plasmovoice.commands.CommandRegistry;
import com.ubivashka.plasmovoice.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class PlasmoVoiceAddon extends JavaPlugin {
	private PluginConfig pluginConfig;
	private PlasmoVoiceSoundPlayer plasmoVoiceSoundPlayer;

	@Override
	public void onEnable() {
		pluginConfig = new PluginConfig(this);
		plasmoVoiceSoundPlayer = new PlasmoVoiceSoundPlayer();
		new CommandRegistry(this);
	}

	public void reload(){
		pluginConfig = new PluginConfig(this);
	}

	public PluginConfig getPluginConfig() {
		return pluginConfig;
	}

	public PlasmoVoiceSoundPlayer getPlasmoVoiceSoundPlayer() {
		return plasmoVoiceSoundPlayer;
	}

}
