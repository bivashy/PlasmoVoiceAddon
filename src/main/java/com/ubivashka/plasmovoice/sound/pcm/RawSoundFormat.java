package com.ubivashka.plasmovoice.sound.pcm;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;
import com.ubivashka.plasmovoice.sound.AbstractSoundFactory;
import com.ubivashka.plasmovoice.sound.ISoundFactory;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class RawSoundFormat implements ISoundFormat {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    private static final String FORMAT_NAME = "RAW";

    @Override
    public boolean isSupported(InputStream audioStream) {
        try {
            AudioSystem.getAudioFileFormat(audioStream);
            return true;
        } catch(UnsupportedAudioFileException | IOException e) {
            return false;
        }
    }

    @Override
    public ISoundFactory newSoundFactory() {
        return new RawSoundFactory(this);
    }

    @Override
    public MusicPlayerSettings getSettings() {
        return PLUGIN.getPluginConfig().getWavMusicPlayerSettings();
    }

    @Override
    public String getName() {
        return FORMAT_NAME;
    }

    public static class RawSoundFactory extends AbstractSoundFactory {
        public RawSoundFactory(ISoundFormat soundFormat) {
            super(soundFormat);
        }

        @Override
        protected AudioInputStream createAudioInputStream(InputStream sourceStream) throws UnsupportedAudioFileException, IOException {
            return AudioSystem.getAudioInputStream(opusCodecHolder.getAudioFormat(), AudioSystem.getAudioInputStream(sourceStream));
        }
    }
}
