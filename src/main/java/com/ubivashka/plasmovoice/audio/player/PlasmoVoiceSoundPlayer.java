package com.ubivashka.plasmovoice.audio.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.controller.PlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.audio.sources.IAudioSource;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;

public class PlasmoVoiceSoundPlayer implements ISoundPlayer {

    @Override
    public ISoundPlaySession playSound(ISound sound, IAudioSource audioSource, ISoundController soundController) {
        if (!(audioSource instanceof IPlayerAudioSource) || !(soundController instanceof PlasmoVoiceSoundController))
            return null;
        IPlayerAudioSource playerAudioSource = (IPlayerAudioSource) audioSource;
        Player player = Bukkit.getPlayer(playerAudioSource.getPlayerUniqueId());
        if (player == null)
            return null;
        return new PlasmoVoiceSoundPlaySession(sound, playerAudioSource, (PlasmoVoiceSoundController) soundController);
    }
}
