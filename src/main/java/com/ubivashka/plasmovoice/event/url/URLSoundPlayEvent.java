package com.ubivashka.plasmovoice.event.url;

import java.net.URL;

import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.event.SoundPlayEvent;
import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class URLSoundPlayEvent extends SoundPlayEvent<URL> {
    public URLSoundPlayEvent(SoundEventModel<URL> soundEventModel, ISoundFormat soundFormat, ISoundController soundController,
            ISoundPlaySession soundPlaySession) {
        super(soundEventModel, soundFormat, soundController, soundPlaySession);
    }
}
