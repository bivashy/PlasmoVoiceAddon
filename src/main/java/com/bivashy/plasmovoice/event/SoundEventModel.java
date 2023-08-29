package com.bivashy.plasmovoice.event;

import java.io.InputStream;

import com.bivashy.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.bivashy.plasmovoice.audio.sources.IPlayerAudioSource;

public class SoundEventModel<T> {
    private PlasmoVoiceSoundPlayer soundPlayer;
    private IPlayerAudioSource playerAudioSource;
    private InputStream inputStream;
    private T source;

    public SoundEventModel(PlasmoVoiceSoundPlayer soundPlayer, IPlayerAudioSource playerAudioSource, InputStream inputStream, T source) {
        this.soundPlayer = soundPlayer;
        this.playerAudioSource = playerAudioSource;
        this.inputStream = inputStream;
        this.source = source;
    }

    public IPlayerAudioSource getPlayerAudioSource() {
        return playerAudioSource;
    }

    public void setPlayerAudioSource(IPlayerAudioSource playerAudioSource) {
        this.playerAudioSource = playerAudioSource;
    }

    public PlasmoVoiceSoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public void setSoundPlayer(PlasmoVoiceSoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }
}