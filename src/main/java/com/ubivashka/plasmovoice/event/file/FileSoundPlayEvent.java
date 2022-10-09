package com.ubivashka.plasmovoice.event.file;

import java.io.File;

import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.event.SoundPlayEvent;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class FileSoundPlayEvent extends SoundPlayEvent<File> {
    public FileSoundPlayEvent(SoundEventModel<File> soundEventModel, ISoundFormat soundFormat,
            ISoundController soundController,
            ISoundPlaySession soundPlaySession) {
        super(soundEventModel, soundFormat, soundController, soundPlaySession);
    }
}
