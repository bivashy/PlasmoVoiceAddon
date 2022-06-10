package com.ubivashka.plasmovoice.sound;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public enum SoundFormat {
    WAV {
        @Override
        public boolean isValid(InputStream stream) {
            try {
                AudioSystem.getAudioFileFormat(stream);
                return true;
            } catch(UnsupportedAudioFileException e) {
                return false;
            } catch(IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    },
    MP3 {
        @Override
        public boolean isValid(InputStream stream) {
            try {
                new MpegAudioFileReader().getAudioFileFormat(stream, 0);
                return true;
            } catch(UnsupportedAudioFileException |
                    IOException e) {
                return false;
            }
        }
    };

    public abstract boolean isValid(InputStream stream);

    public static SoundFormat findMatchingSoundFormat(InputStream stream) {
        return Arrays.stream(values()).filter(soundFormat -> soundFormat.isValid(stream)).findFirst().orElse(null);
    }
}
