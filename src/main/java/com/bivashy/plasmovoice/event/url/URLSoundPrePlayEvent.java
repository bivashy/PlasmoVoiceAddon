package com.bivashy.plasmovoice.event.url;

import java.net.URL;

import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPrePlayEvent;
import com.bivashy.plasmovoice.sound.ISoundFormat;

public class URLSoundPrePlayEvent extends SoundPrePlayEvent<URL> {
    public URLSoundPrePlayEvent(SoundEventModel<URL> soundEventModel,
                                ISoundFormat soundFormat) {
        super(soundEventModel, soundFormat);
    }
}
