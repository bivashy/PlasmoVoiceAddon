package com.ubivashka.plasmovoice.audio.sources;

import java.util.Optional;

import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.sound.ISound;

public interface IAudioSource {
    /**
     * Send audio data in specific format.
     *
     * @param sound           - Sound that proceeded with {@link com.ubivashka.plasmovoice.audio.codecs.ICodecHolder}.
     * @param soundController - Sound controller.
     * @return session of the sound that will need for controlling playing sound
     */
    ISoundPlaySession sendAudioData(ISound sound, ISoundController soundController);

    /**
     * Last session that was sent with method {@link IAudioSource#sendAudioData(ISound, ISoundController)}}
     *
     * @return Last sent session, may be null.
     */
    Optional<ISoundPlaySession> getCurrentSession();

    /**
     * Set last session. Mostly required for ISoundPlayer.
     *
     * @param lastSession new last session
     */
    void setCurrentSession(ISoundPlaySession lastSession);
}
