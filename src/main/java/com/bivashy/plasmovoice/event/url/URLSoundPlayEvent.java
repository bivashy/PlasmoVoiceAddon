package com.bivashy.plasmovoice.event.url;

import java.net.URL;

import com.bivashy.plasmovoice.audio.player.controller.ISoundController;
import com.bivashy.plasmovoice.audio.player.session.ISoundPlaySession;
import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPlayEvent;
import com.bivashy.plasmovoice.sound.ISoundFormat;

public class URLSoundPlayEvent extends SoundPlayEvent<URL> {
    public URLSoundPlayEvent(SoundEventModel<URL> soundEventModel, ISoundFormat soundFormat, ISoundController soundController,
                             ISoundPlaySession soundPlaySession) {
        super(soundEventModel, soundFormat, soundController, soundPlaySession);
    }
}
