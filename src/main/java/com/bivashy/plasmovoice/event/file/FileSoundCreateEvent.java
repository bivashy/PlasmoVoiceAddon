package com.bivashy.plasmovoice.event.file;

import java.io.File;

import com.bivashy.plasmovoice.event.SoundCreateEvent;
import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.sound.ISound;

public class FileSoundCreateEvent extends SoundCreateEvent<File> {
    public FileSoundCreateEvent(SoundEventModel<File> soundEventModel, ISound sound) {
        super(soundEventModel, sound);
    }
}
