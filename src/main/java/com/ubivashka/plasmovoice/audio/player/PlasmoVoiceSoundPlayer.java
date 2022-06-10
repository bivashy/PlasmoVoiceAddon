package com.ubivashka.plasmovoice.audio.player;

import com.ubivashka.plasmovoice.audio.codecs.ICodecHolder;
import com.ubivashka.plasmovoice.audio.codecs.OpusCodecHolder;
import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.controller.PlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.audio.sources.IAudioSource;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import su.plo.voice.PlasmoVoice;

public class PlasmoVoiceSoundPlayer implements ISoundPlayer {
    private final OpusCodecHolder opusCodecHolder = new OpusCodecHolder();

    @Override
    public ISoundPlaySession playSound(ISound sound, IAudioSource audioSource, ISoundController soundController) {
        if (!(audioSource instanceof IPlayerAudioSource) || !(soundController instanceof PlasmoVoiceSoundController))
            return null;
        updateCodecHolder();
        IPlayerAudioSource playerAudioSource = (IPlayerAudioSource) audioSource;
        Player player = Bukkit.getPlayer(playerAudioSource.getPlayerUniqueId());
        if (player == null)
            return null;
        return new PlasmoVoiceSoundPlaySession(sound, playerAudioSource, (PlasmoVoiceSoundController) soundController);
    }

    private void updateCodecHolder() {
        opusCodecHolder.setSampleRate(PlasmoVoice.getInstance().getVoiceConfig().getSampleRate());
    }

    @Override
    public ICodecHolder getCodecHolder() {
        return opusCodecHolder;
    }

}
