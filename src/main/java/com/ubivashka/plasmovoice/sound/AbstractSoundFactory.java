package com.ubivashka.plasmovoice.sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.ubivashka.plasmovoice.audio.codecs.OpusCodecHolder;
import com.ubivashka.plasmovoice.sound.util.AudioInputStreamReader;
import com.ubivashka.plasmovoice.sound.util.VolumeAdjuster;

public abstract class AbstractSoundFactory implements ISoundFactory {
    protected final ISoundFormat soundFormat;

    public AbstractSoundFactory(ISoundFormat soundFormat) {
        this.soundFormat = soundFormat;
    }

    private static final VolumeAdjuster VOLUME_ADJUSTER = new VolumeAdjuster();
    protected final OpusCodecHolder opusCodecHolder = OpusCodecHolder.newCodecHolder();

    @Override
    public ISound createSound(InputStream audioStream) {
        try (InputStream ignored = audioStream) {
            AudioInputStream audioInputStream = createAudioInputStream(audioStream);
            return createSound(audioInputStream);
        } catch(IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        opusCodecHolder.closeEncoder();
        return ISound.of(new ArrayList<>(), soundFormat);
    }

    public ISound createSound(AudioInputStream audioInputStream) {
        try {
            opusCodecHolder.setBitrate(soundFormat.getSettings().getBitrate());
            ISound result =
                    ISound.of(new AudioInputStreamReader(audioInputStream).withConverter(
                                            data -> VOLUME_ADJUSTER.adjust(data, soundFormat.getSettings().getVolume() / 100)).withConverter(opusCodecHolder::encode)
                                    .read(opusCodecHolder.getFrameSize()),
                            soundFormat);
            opusCodecHolder.closeEncoder();
            return result;
        } catch(IOException e) {
            e.printStackTrace();
            opusCodecHolder.closeEncoder();
            return ISound.of(new ArrayList<>(), soundFormat);
        }
    }

    protected abstract AudioInputStream createAudioInputStream(InputStream sourceStream) throws UnsupportedAudioFileException, IOException;
}
