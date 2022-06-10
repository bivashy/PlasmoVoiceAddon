package com.ubivashka.plasmovoice.audio.sources;

import com.ubivashka.plasmovoice.audio.player.ISoundPlayer;
import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.sound.ISound;

import java.util.UUID;

public class PlayerAudioSource extends AbstractPlayerAudioSource {
    private final UUID playerUniqueId;

    public PlayerAudioSource(UUID playerUniqueId, ISoundPlayer soundPlayer) {
        super(soundPlayer);
        this.playerUniqueId = playerUniqueId;
    }

    @Override
    public UUID getPlayerUniqueId() {
        return playerUniqueId;
    }

    @Override
    public void sendAudioData(ISound sound, ISoundController soundController) {
        soundPlayer.playSound(sound, this, soundController);
    }


}
