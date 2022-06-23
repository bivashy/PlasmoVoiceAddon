package com.ubivashka.plasmovoice.audio.player.controller;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class PlasmoVoiceSoundController implements ISoundController {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    private final ISoundFormat soundFormat;
    private int distance;
    private PlasmoVoiceSoundPlaySession soundPlaySession;
    private boolean playing = true;

    public PlasmoVoiceSoundController(ISoundFormat soundFormat) {
        this(soundFormat, 100);
    }

    public PlasmoVoiceSoundController(ISoundFormat soundFormat, int distance) {
        this.soundFormat = soundFormat;
        this.distance = distance;
    }

    @Override
    public void stop() {
        playing = false;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    public MusicPlayerSettings getMusicPlayerSettings() {
        return soundFormat.getSettings();
    }

    public ISoundFormat getSoundFormat() {
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
