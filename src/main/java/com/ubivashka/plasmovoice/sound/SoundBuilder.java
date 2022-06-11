package com.ubivashka.plasmovoice.sound;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.sound.mp3.MP3Sound;
import com.ubivashka.plasmovoice.sound.pcm.AudioStreamSound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundBuilder {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);

    private final InputStream inputStream;
    private SoundFormat soundFormat;

    public SoundBuilder(InputStream inputStream) {
        if (!inputStream.markSupported())
            inputStream = new BufferedInputStream(inputStream);
        this.inputStream = inputStream;
        this.soundFormat = SoundFormat.findMatchingSoundFormat(this.inputStream);
    }

    public SoundBuilder withSoundFormat(SoundFormat soundFormat) {
        this.soundFormat = soundFormat;
        return this;
    }

    public ISound build() throws UnsupportedAudioFileException, IOException {
        ISound sound = null;
        inputStream.reset();
        if (soundFormat == null)
            return null;
        switch (soundFormat) {
            case WAV:
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
                sound = new AudioStreamSound(audioInputStream, PLUGIN.getPlasmoVoiceSoundPlayer().getCodecHolder(), true);
                break;
            case MP3:
                sound = new MP3Sound(inputStream, PLUGIN.getPlasmoVoiceSoundPlayer());
                break;
        }
        this.inputStream.close();
        return sound;
    }
}
