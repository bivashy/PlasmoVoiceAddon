package com.ubivashka.plasmovoice.event.file;

import java.io.File;

import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.event.SoundPreCreateEvent;

public class FileSoundPreCreateEvent extends SoundPreCreateEvent<File> {
    public FileSoundPreCreateEvent(SoundEventModel<File> soundEventModel) {
        super(soundEventModel);
    }
}
