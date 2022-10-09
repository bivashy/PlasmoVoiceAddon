package com.ubivashka.plasmovoice.event.url;

import java.net.URL;

import com.ubivashka.plasmovoice.event.SoundCreateEvent;
import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.sound.ISound;

public class URLSoundCreateEvent extends SoundCreateEvent<URL> {
    public URLSoundCreateEvent(SoundEventModel<URL> soundEventModel, ISound sound) {
        super(soundEventModel, sound);
    }
}
