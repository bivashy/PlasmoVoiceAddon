package com.bivashy.plasmovoice.event.file;

import java.io.File;

import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundFormatPreCreateEvent;

public class FileSoundFormatPreCreateEvent extends SoundFormatPreCreateEvent<File> {
    public FileSoundFormatPreCreateEvent(SoundEventModel<File> soundEventModel) {
        super(soundEventModel);
    }
}
