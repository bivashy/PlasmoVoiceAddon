package com.ubivashka.plasmovoice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.bukkit.plugin.java.JavaPlugin;

import com.ubivashka.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.commands.CommandRegistry;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.sound.holder.SoundFormatHolder;
import com.ubivashka.plasmovoice.sound.holder.json.CachedSoundJsonHolder;
import com.ubivashka.plasmovoice.sound.mp3.Mp3SoundFormat;
import com.ubivashka.plasmovoice.sound.pcm.RawSoundFormat;

public class PlasmoVoiceAddon extends JavaPlugin {
    private final Map<UUID, PlasmoVoiceSoundPlayer> playerSoundPlayers = new HashMap<>();
    private final SoundFormatHolder soundFormatHolder = new SoundFormatHolder();
    private CachedSoundJsonHolder cachedSoundHolder;
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        pluginConfig = new PluginConfig(this);
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

    public SoundFormatHolder getSoundFormatHolder() {
        return soundFormatHolder;
    }

    public CachedSoundJsonHolder getCachedSoundHolder() {
        return cachedSoundHolder;
    }

    public Map<UUID, PlasmoVoiceSoundPlayer> getPlayerSoundPlayers() {
        return Collections.unmodifiableMap(playerSoundPlayers);
    }

    public PlasmoVoiceSoundPlayer getPlasmoVoiceSoundPlayer(UUID uuid) {
        return getPlasmoVoiceSoundPlayer(uuid, () -> new PlayerAudioSource(uuid));
    }

    public PlasmoVoiceSoundPlayer getPlasmoVoiceSoundPlayer(UUID uuid, Supplier<IPlayerAudioSource> defaultAudioSource) {
        return playerSoundPlayers.getOrDefault(uuid, new PlasmoVoiceSoundPlayer(defaultAudioSource.get()));
    }
}
