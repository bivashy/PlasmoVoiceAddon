package com.bivashy.plasmovoice.event.file;

import java.io.File;

import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPreCreateEvent;

public class FileSoundPreCreateEvent extends SoundPreCreateEvent<File> {
    public FileSoundPreCreateEvent(SoundEventModel<File> soundEventModel) {
        super(soundEventModel);
    }
}
