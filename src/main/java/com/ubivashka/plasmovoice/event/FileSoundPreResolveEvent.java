package com.ubivashka.plasmovoice.event;

import java.io.File;
import java.io.InputStream;

import com.ubivashka.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;

public class FileSoundPreResolveEvent extends SoundPreResolveEvent {
    public FileSoundPreResolveEvent(FileSoundResolveModel soundResolveModel) {
        super(soundResolveModel);
    }

    public FileSoundResolveModel getFileSoundResolveModel() {
        return (FileSoundResolveModel) getSoundResolveModel();
    }

    public static class FileSoundResolveModel extends SoundResolveModel {
        private File file;

        public FileSoundResolveModel(PlasmoVoiceSoundPlayer soundPlayer, IPlayerAudioSource playerAudioSource, File file, InputStream inputStream) {
            super(soundPlayer, playerAudioSource, inputStream);
            this.file = file;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }
    }
}
