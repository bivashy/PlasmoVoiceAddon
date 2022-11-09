package com.ubivashka.plasmovoice.event.url;

import java.net.URL;

import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.event.SoundFormatPreCreateEvent;

public class URLSoundFormatPreCreateEvent extends SoundFormatPreCreateEvent<URL> {
    public URLSoundFormatPreCreateEvent(SoundEventModel<URL> soundEventModel) {
        super(soundEventModel);
    }
}
