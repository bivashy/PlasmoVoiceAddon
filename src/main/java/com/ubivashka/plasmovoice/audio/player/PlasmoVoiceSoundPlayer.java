package com.ubivashka.plasmovoice.audio.player;

import java.util.UUID;

import com.ubivashka.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.audio.sources.IAudioSource;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;

public class PlasmoVoiceSoundPlayer implements ISoundPlayer {
    private final IPlayerAudioSource playerAudioSource;

    public PlasmoVoiceSoundPlayer(IPlayerAudioSource playerAudioSource) {
        this.playerAudioSource = playerAudioSource;
    }

    public PlasmoVoiceSoundPlayer(UUID uuid) {
        this(new PlayerAudioSource(uuid));
    }

    @Override
    public ISoundPlaySession playSound(ISound sound, ISoundController soundController) {
        if (!(soundController instanceof IPlasmoVoiceSoundController))
            return null;
        ISoundPlaySession soundPlaySession = new PlasmoVoiceSoundPlaySession(sound, playerAudioSource, (IPlasmoVoiceSoundController) soundController);
        playerAudioSource.setLastSession(soundPlaySession);
        return soundPlaySession;
    }

    @Override
    public IAudioSource getSource() {
        return playerAudioSource;
    }
}
