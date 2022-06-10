package com.ubivashka.plasmovoice.audio.player.controller;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;
import com.ubivashka.plasmovoice.sound.SoundFormat;

public class PlasmoVoiceSoundController implements ISoundController {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    private final SoundFormat soundFormat;
    private int distance;
    private PlasmoVoiceSoundPlaySession soundPlaySession;
    private boolean playing = true;

    public PlasmoVoiceSoundController(SoundFormat soundFormat) {
        this(soundFormat, 100);
    }

    public PlasmoVoiceSoundController(SoundFormat soundFormat, int distance) {
        this.soundFormat = soundFormat;
        this.distance = distance;
    }

    @Override
    public void stop() {
        playing = false;
    }

    @Override
    public boolean isPlaying(){
        return playing;
    }

    public MusicPlayerSettings getMusicPlayerSettings() {
        switch (soundFormat) {
            case MP3:
                return PLUGIN.getPluginConfig().getMp3MusicPlayerSettings();
            case WAV:
                return PLUGIN.getPluginConfig().getWavMusicPlayerSettings();
            default:
                throw new UnsupportedOperationException("Unrecognized sound format! Cannot find MusicPlayerSettings");
        }
    }

    public SoundFormat getSoundFormat() {
        return soundFormat;
    }

    public int getDistance() {
        return distance;
    }

    public PlasmoVoiceSoundController setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public PlasmoVoiceSoundPlaySession getSoundPlaySession() {
        return soundPlaySession;
    }

    public PlasmoVoiceSoundController setSoundPlaySession(PlasmoVoiceSoundPlaySession soundPlaySession) {
        this.soundPlaySession = soundPlaySession;
        return this;
    }
}
