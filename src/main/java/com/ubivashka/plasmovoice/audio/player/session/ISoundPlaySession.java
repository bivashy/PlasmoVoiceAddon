package com.ubivashka.plasmovoice.audio.player.session;

import com.ubivashka.plasmovoice.sound.ISound;

public interface ISoundPlaySession {
    ISound getSound();

    void pause();

    void continuePlaying();

    void end();

    boolean isPaused();

    boolean isEnded();
}
