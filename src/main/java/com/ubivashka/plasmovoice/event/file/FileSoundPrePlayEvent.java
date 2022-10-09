package com.ubivashka.plasmovoice.event.file;

import java.io.File;

import com.ubivashka.plasmovoice.event.SoundPrePlayEvent;
import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class FileSoundPrePlayEvent extends SoundPrePlayEvent<File> {
    public FileSoundPrePlayEvent(SoundEventModel<File> soundEventModel,
            ISoundFormat soundFormat) {
        super(soundEventModel, soundFormat);
    }
}
