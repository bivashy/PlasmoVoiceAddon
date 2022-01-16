package com.ubivashka.plasmovoice;

import org.bukkit.plugin.java.JavaPlugin;

import com.ubivashka.plasmovoice.audio.recorder.PlasmoVoiceSoundPlayer;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.executors.PlayFileSoundExecutor;
import com.ubivashka.plasmovoice.executors.PlayURLSoundExecutor;

public class PlasmoVoiceAddon extends JavaPlugin {
	private PluginConfig pluginConfig;
	private PlasmoVoiceSoundPlayer plasmoVoiceSoundPlayer;

	@Override
	public void onEnable() {
		pluginConfig = new PluginConfig(this);
		plasmoVoiceSoundPlayer = new PlasmoVoiceSoundPlayer();
		getCommand("musicurl").setExecutor(new PlayURLSoundExecutor());
		getCommand("musicfile").setExecutor(new PlayFileSoundExecutor());
	}

	public PluginConfig getPluginConfig() {
		return pluginConfig;
	}

	public PlasmoVoiceSoundPlayer getPlasmoVoiceSoundPlayer() {
		return plasmoVoiceSoundPlayer;
	}

}
