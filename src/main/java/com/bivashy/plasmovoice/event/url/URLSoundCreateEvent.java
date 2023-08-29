package com.bivashy.plasmovoice.event.url;

import java.net.URL;

import com.bivashy.plasmovoice.event.SoundCreateEvent;
import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.sound.ISound;

public class URLSoundCreateEvent extends SoundCreateEvent<URL> {
    public URLSoundCreateEvent(SoundEventModel<URL> soundEventModel, ISound sound) {
        super(soundEventModel, sound);
    }
}
