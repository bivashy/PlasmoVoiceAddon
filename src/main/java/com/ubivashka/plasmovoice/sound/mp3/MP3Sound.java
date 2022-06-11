package com.ubivashka.plasmovoice.sound.mp3;

import com.ubivashka.plasmovoice.audio.player.ISoundPlayer;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.SoundFormat;
import com.ubivashka.plasmovoice.sound.pcm.AudioStreamSound;
import javazoom.spi.mpeg.sampled.convert.MpegFormatConversionProvider;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author Ubivashka Данный класс конвертирует mp3 файл, либо ссылку на файл в
 * pcm формат Для этого используется данная
 * <a href="https://github.com/kevinstadler/JavaMP3">библиотека</a>
 */
public class MP3Sound implements ISound {

    private AudioStreamSound convertedSound;

    public MP3Sound(InputStream musicInputStream, ISoundPlayer player) throws IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = new MpegAudioFileReader().getAudioInputStream(musicInputStream);
        AudioFormat baseFormat = audioInputStream.getFormat();
        AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
        audioInputStream = new MpegFormatConversionProvider().getAudioInputStream(targetFormat, audioInputStream);
        convertedSound = new AudioStreamSound(audioInputStream, player.createCodecHolder(), true);
    }

    @Override
    public SoundFormat getSoundFormat() {
        return SoundFormat.MP3;
    }

    @Override
    public List<byte[]> getDataList() {
        if (convertedSound == null)
            return Collections.emptyList();
        return convertedSound.getDataList();
    }
}
