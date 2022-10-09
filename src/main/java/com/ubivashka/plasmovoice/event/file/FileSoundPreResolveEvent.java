package com.ubivashka.plasmovoice.event.file;

import java.io.File;

import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.event.SoundPreResolveEvent;

public class FileSoundPreResolveEvent extends SoundPreResolveEvent<File> {
    public FileSoundPreResolveEvent(SoundEventModel<File> soundEventModel) {
        super(soundEventModel);
    }
}
