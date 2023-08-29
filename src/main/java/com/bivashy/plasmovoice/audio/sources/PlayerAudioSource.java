package com.bivashy.plasmovoice.audio.sources;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.audio.player.controller.ISoundController;
import com.bivashy.plasmovoice.audio.player.session.ISoundPlaySession;
import com.bivashy.plasmovoice.sound.ISound;

public class PlayerAudioSource implements IPlayerAudioSource {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    private final UUID playerUniqueId;
    private ISoundPlaySession currentSession;

    public PlayerAudioSource(UUID playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }


    @Override
    public UUID getPlayerUniqueId() {
        return playerUniqueId;
    }

    @Override
    public Location getPlayerLocation() {
        Player player = Bukkit.getPlayer(playerUniqueId);
        if (player == null)
            throw new UnsupportedOperationException("Cannot get location of null player");
        return player.getLocation();
    }

    @Override
    public ISoundPlaySession sendAudioData(ISound sound, ISoundController soundController) {
        currentSession = PLUGIN.getPlasmoVoiceSoundPlayer(playerUniqueId).playSound(sound, soundController);
        return currentSession;
    }

    @Override
    public Optional<ISoundPlaySession> getCurrentSession() {
        return Optional.ofNullable(currentSession);
    }

    @Override
    public void setCurrentSession(ISoundPlaySession lastSession) {
        this.currentSession = lastSession;
    }
}
