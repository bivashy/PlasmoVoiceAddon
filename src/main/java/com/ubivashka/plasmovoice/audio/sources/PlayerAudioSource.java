package com.ubivashka.plasmovoice.audio.sources;

import com.ubivashka.plasmovoice.audio.player.ISoundPlayer;
import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.sound.ISound;

import java.util.UUID;

public class PlayerAudioSource extends AbstractPlayerAudioSource {
    private final UUID playerUniqueId;
    private ISoundPlaySession lastSession;

    public PlayerAudioSource(UUID playerUniqueId, ISoundPlayer soundPlayer) {
        super(soundPlayer);
        this.playerUniqueId = playerUniqueId;
    }

    @Override
    public UUID getPlayerUniqueId() {
        return playerUniqueId;
    }

    @Override
    public ISoundPlaySession sendAudioData(ISound sound, ISoundController soundController) {
        lastSession = soundPlayer.playSound(sound, this, soundController);
        return lastSession;
    }

    @Override
    public ISoundPlaySession getLastSession() {
        return lastSession;
    }
}
