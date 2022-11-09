package com.ubivashka.plasmovoice.event.url;

import java.net.URL;

import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.event.SoundPreCreateEvent;

public class URLSoundPreCreateEvent extends SoundPreCreateEvent<URL> {
    public URLSoundPreCreateEvent(SoundEventModel<URL> soundEventModel) {
        super(soundEventModel);
    }
}
