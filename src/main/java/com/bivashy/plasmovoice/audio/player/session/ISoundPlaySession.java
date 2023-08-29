package com.bivashy.plasmovoice.audio.player.session;

import com.bivashy.plasmovoice.sound.ISound;

public interface ISoundPlaySession {
    ISound getSound();

    void pause();

    void continuePlaying();

    void end();

    boolean isPaused();

    boolean isEnded();
}
