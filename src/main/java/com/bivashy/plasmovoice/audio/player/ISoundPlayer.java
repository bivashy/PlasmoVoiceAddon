package com.bivashy.plasmovoice.audio.player;

import com.bivashy.plasmovoice.audio.player.controller.ISoundController;
import com.bivashy.plasmovoice.audio.player.session.ISoundPlaySession;
import com.bivashy.plasmovoice.audio.sources.IAudioSource;
import com.bivashy.plasmovoice.sound.ISound;

public interface ISoundPlayer {
    IAudioSource getSource();

    /**
     * @param sound            - Playing sound
     * @param soundController  - Sound controller.
     * @return {@link ISoundPlaySession} - Sound session, can cancel music playing, check if music playing ended.
     */
    ISoundPlaySession playSound(ISound sound, ISoundController soundController);
}
