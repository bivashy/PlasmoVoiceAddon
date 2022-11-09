package com.ubivashka.plasmovoice.event.file;

import java.io.File;

import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.event.SoundFormatPreCreateEvent;

public class FileSoundFormatPreCreateEvent extends SoundFormatPreCreateEvent<File> {
    public FileSoundFormatPreCreateEvent(SoundEventModel<File> soundEventModel) {
        super(soundEventModel);
    }
}
