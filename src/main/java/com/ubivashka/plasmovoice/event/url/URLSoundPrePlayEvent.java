package com.ubivashka.plasmovoice.event.url;

import java.net.URL;

import com.ubivashka.plasmovoice.event.SoundPrePlayEvent;
import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class URLSoundPrePlayEvent extends SoundPrePlayEvent<URL> {
    public URLSoundPrePlayEvent(SoundEventModel<URL> soundEventModel,
            ISoundFormat soundFormat) {
        super(soundEventModel, soundFormat);
    }
}
