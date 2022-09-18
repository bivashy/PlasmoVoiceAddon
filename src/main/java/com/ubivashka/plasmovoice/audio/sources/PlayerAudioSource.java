package com.ubivashka.plasmovoice.audio.sources;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.ISoundPlayer;
import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.sound.ISound;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerAudioSource extends AbstractPlayerAudioSource {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    private final UUID playerUniqueId;
    private ISoundPlaySession lastSession;

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
        lastSession = PLUGIN.getPlasmoVoiceSoundPlayer(playerUniqueId).playSound(sound, soundController);
        return lastSession;
    }

    @Override
    public ISoundPlaySession getLastSession() {
        return lastSession;
    }

    @Override
    public void setLastSession(ISoundPlaySession lastSession) {
        this.lastSession = lastSession;
    }
}
