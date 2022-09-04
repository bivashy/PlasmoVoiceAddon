package com.ubivashka.plasmovoice.sound;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;

public interface ISoundFormat {
    /**
     * @return Can this sound format play this AudioInputStream
     */
    boolean isSupported(InputStream audioStream);

    default boolean isSupported(File file, InputStream fileStream) {
        return isSupported(fileStream);
    }

    default boolean isSupported(URL url, InputStream urlStream) {
        return isSupported(urlStream);
    }

    MusicPlayerSettings getSettings();

    ISoundFactory newSoundFactory();
}
