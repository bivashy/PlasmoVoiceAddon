package com.bivashy.plasmovoice.event.file;

import java.io.File;

import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPrePlayEvent;
import com.bivashy.plasmovoice.sound.ISoundFormat;

public class FileSoundPrePlayEvent extends SoundPrePlayEvent<File> {
    public FileSoundPrePlayEvent(SoundEventModel<File> soundEventModel,
                                 ISoundFormat soundFormat) {
        super(soundEventModel, soundFormat);
    }
}
