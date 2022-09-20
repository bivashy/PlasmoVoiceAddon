package com.ubivashka.plasmovoice.audio.player.controller;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class DefaultPlasmoVoiceSoundController implements IPlasmoVoiceSoundController {
    private final ISoundFormat soundFormat;
    private boolean playing = true;
    private int distance;
    private boolean canHearSource = true;
    private boolean turnOffOnLeave = true;

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

    @Override
    public boolean canHearSource() {
        return canHearSource;
    }

    @Override
    public void setHearSource(boolean canHearSource) {
        this.canHearSource = canHearSource;
    }

    @Override
    public boolean isTurnOffOnLeave() {
        return turnOffOnLeave;
    }

    @Override
    public void setTurnOffOnLeave(boolean turnOffOnLeave) {
        this.turnOffOnLeave = turnOffOnLeave;
    }
}
