package com.ubivashka.plasmovoice.sound;

import java.io.InputStream;

import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;

public interface ISoundFormat {
    /**
     * @return Can this sound format play this AudioInputStream
     */
    boolean isSupported(InputStream audioStream);

    MusicPlayerSettings getSettings();

    ISoundFactory newSoundFactory();
}
