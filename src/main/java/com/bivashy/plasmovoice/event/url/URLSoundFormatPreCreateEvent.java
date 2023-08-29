package com.bivashy.plasmovoice.event.url;

import java.net.URL;

import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundFormatPreCreateEvent;

public class URLSoundFormatPreCreateEvent extends SoundFormatPreCreateEvent<URL> {
    public URLSoundFormatPreCreateEvent(SoundEventModel<URL> soundEventModel) {
        super(soundEventModel);
    }
}
