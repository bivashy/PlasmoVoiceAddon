package com.bivashy.plasmovoice.event.file;

import java.io.File;

import com.bivashy.plasmovoice.audio.player.controller.ISoundController;
import com.bivashy.plasmovoice.audio.player.session.ISoundPlaySession;
import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPlayEvent;
import com.bivashy.plasmovoice.sound.ISoundFormat;

public class FileSoundPlayEvent extends SoundPlayEvent<File> {
    public FileSoundPlayEvent(SoundEventModel<File> soundEventModel, ISoundFormat soundFormat,
                              ISoundController soundController,
                              ISoundPlaySession soundPlaySession) {
        super(soundEventModel, soundFormat, soundController, soundPlaySession);
    }
}
