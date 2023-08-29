package com.bivashy.plasmovoice.event.url;

import java.net.URL;

import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPreResolveEvent;

public class URLSoundPreResolveEvent extends SoundPreResolveEvent<URL> {
    public URLSoundPreResolveEvent(SoundEventModel<URL> soundEventModel) {
        super(soundEventModel);
    }
}
