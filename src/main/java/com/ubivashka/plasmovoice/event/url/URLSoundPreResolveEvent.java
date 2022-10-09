package com.ubivashka.plasmovoice.event.url;

import java.net.URL;

import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.event.SoundPreResolveEvent;

public class URLSoundPreResolveEvent extends SoundPreResolveEvent<URL> {
    public URLSoundPreResolveEvent(SoundEventModel<URL> soundEventModel) {
        super(soundEventModel);
    }
}
