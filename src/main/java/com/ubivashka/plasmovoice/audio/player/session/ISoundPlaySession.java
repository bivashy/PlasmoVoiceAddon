package com.ubivashka.plasmovoice.audio.player.session;

import com.ubivashka.plasmovoice.sound.ISound;

public interface ISoundPlaySession {
    ISound getSound();

    boolean isEnded();
}
