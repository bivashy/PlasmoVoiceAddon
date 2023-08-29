package com.bivashy.plasmovoice.event.url;

import java.net.URL;

import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPreCreateEvent;

public class URLSoundPreCreateEvent extends SoundPreCreateEvent<URL> {
    public URLSoundPreCreateEvent(SoundEventModel<URL> soundEventModel) {
        super(soundEventModel);
    }
}
