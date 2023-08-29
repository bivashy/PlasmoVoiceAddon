package com.bivashy.plasmovoice.sound.holder;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

import com.bivashy.plasmovoice.sound.ISoundFormat;

public class SoundFormatHolder extends AbstractHolder<ISoundFormat> {
    public Optional<ISoundFormat> findFirstFormat(URL url, InputStream inputStream) {
        return findFirstByPredicate(format -> format.isSupported(url, inputStream));
    }

    public Optional<ISoundFormat> findFirstFormat(File file, InputStream inputStream) {
        return findFirstByPredicate(format -> format.isSupported(file, inputStream));
    }
}
