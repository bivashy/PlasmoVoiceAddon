package com.ubivashka.plasmovoice.sound;

import java.io.InputStream;

public interface ISoundFactory {
    ISound createSound(InputStream stream);
}
