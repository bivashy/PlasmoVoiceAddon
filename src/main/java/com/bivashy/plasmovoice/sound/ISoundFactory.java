package com.bivashy.plasmovoice.sound;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public interface ISoundFactory {
    ISound createSound(InputStream stream);

    default ISound createSound(File file, InputStream fileStream) {
        return createSound(fileStream);
    }

    default ISound createSound(URL url, InputStream urlStream) {
        return createSound(urlStream);
    }
}
