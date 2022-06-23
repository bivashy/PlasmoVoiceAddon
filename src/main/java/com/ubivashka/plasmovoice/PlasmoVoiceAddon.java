package com.ubivashka.plasmovoice;

import org.bukkit.plugin.java.JavaPlugin;

import com.ubivashka.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.ubivashka.plasmovoice.commands.CommandRegistry;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.sound.holder.SoundFormatHolder;
import com.ubivashka.plasmovoice.sound.holder.json.CachedSoundJsonHolder;
import com.ubivashka.plasmovoice.sound.mp3.Mp3SoundFormat;
import com.ubivashka.plasmovoice.sound.pcm.RawSoundFormat;

public class PlasmoVoiceAddon extends JavaPlugin {
    private final SoundFormatHolder soundFormatHolder = new SoundFormatHolder();
    private PlasmoVoiceSoundPlayer plasmoVoiceSoundPlayer;
    private CachedSoundJsonHolder cachedSoundHolder;
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        pluginConfig = new PluginConfig(this);
        plasmoVoiceSoundPlayer = new PlasmoVoiceSoundPlayer();
        new CommandRegistry(this);

        soundFormatHolder.add(new RawSoundFormat()).add(new Mp3SoundFormat());
        cachedSoundHolder = new CachedSoundJsonHolder(this);
    }

    @Override
    public void onDisable() {
        if (cachedSoundHolder != null)
            cachedSoundHolder.save();
    }

    public void reload() {
        reloadConfig();
        pluginConfig = new PluginConfig(this);
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }

    public PlasmoVoiceSoundPlayer getPlasmoVoiceSoundPlayer() {
        return plasmoVoiceSoundPlayer;
    }

    public SoundFormatHolder getSoundFormatHolder() {
        return soundFormatHolder;
    }

    public CachedSoundJsonHolder getCachedSoundHolder() {
        return cachedSoundHolder;
    }
}
