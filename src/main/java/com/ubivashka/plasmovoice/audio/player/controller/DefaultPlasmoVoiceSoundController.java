package com.ubivashka.plasmovoice.audio.player.controller;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class DefaultPlasmoVoiceSoundController implements IPlasmoVoiceSoundController {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    private final ISoundFormat soundFormat;
    private int distance;
    private PlasmoVoiceSoundPlaySession soundPlaySession;
    private boolean playing = true;

    public DefaultPlasmoVoiceSoundController(ISoundFormat soundFormat, int distance) {
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

    public DefaultPlasmoVoiceSoundController setDistance(int distance) {
        this.distance = distance;
        return this;
    }
}
