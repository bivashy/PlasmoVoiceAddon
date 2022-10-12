package com.ubivashka.plasmovoice.sound.mp3;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;
import com.ubivashka.plasmovoice.sound.AbstractSoundFactory;
import com.ubivashka.plasmovoice.sound.ISoundFactory;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

import javazoom.spi.mpeg.sampled.convert.MpegFormatConversionProvider;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

public class Mp3SoundFormat implements ISoundFormat {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    private static final String FORMAT_NAME = "MP3";
    private static final MpegAudioFileReader MPEG_AUDIO_FILE_READER = new MpegAudioFileReader();

    @Override
    public boolean isSupported(InputStream audioStream) {
        try {
            MPEG_AUDIO_FILE_READER.getAudioFileFormat(audioStream, AudioSystem.NOT_SPECIFIED);
            return true;
        } catch(UnsupportedAudioFileException | IOException e) {
            return false;
        }
    }

    @Override
    public MusicPlayerSettings getSettings() {
        return PLUGIN.getPluginConfig().getMp3MusicPlayerSettings();
    }

    @Override
    public ISoundFactory newSoundFactory() {
        return new Mp3SoundFactory(this);
    }

    @Override
    public String getName() {
        return FORMAT_NAME;
    }

    public static class Mp3SoundFactory extends AbstractSoundFactory {

        public Mp3SoundFactory(ISoundFormat soundFormat) {
            super(soundFormat);
        }

        @Override
        protected AudioInputStream createAudioInputStream(InputStream sourceStream) throws UnsupportedAudioFileException, IOException {
            AudioInputStream audioInputStream = new MpegAudioFileReader().getAudioInputStream(sourceStream);
            AudioFormat baseFormat = audioInputStream.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            return AudioSystem.getAudioInputStream(opusCodecHolder.getAudioFormat(), new MpegFormatConversionProvider().getAudioInputStream(targetFormat, audioInputStream));
        }
    }
}
