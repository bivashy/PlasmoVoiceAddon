package com.bivashy.plasmovoice.event.file;

import java.io.File;

import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPreResolveEvent;

public class FileSoundPreResolveEvent extends SoundPreResolveEvent<File> {
    public FileSoundPreResolveEvent(SoundEventModel<File> soundEventModel) {
        super(soundEventModel);
    }
}
