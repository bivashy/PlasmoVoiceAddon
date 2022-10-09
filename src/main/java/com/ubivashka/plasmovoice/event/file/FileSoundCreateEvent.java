package com.ubivashka.plasmovoice.event.file;

import java.io.File;

import com.ubivashka.plasmovoice.event.SoundCreateEvent;
import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.sound.ISound;

public class FileSoundCreateEvent extends SoundCreateEvent<File> {
    public FileSoundCreateEvent(SoundEventModel<File> soundEventModel, ISound sound) {
        super(soundEventModel, sound);
    }
}
