package com.ubivashka.plasmovoice.audio.player.controller;

import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public interface IPlasmoVoiceSoundController extends ISoundController {
    MusicPlayerSettings getMusicPlayerSettings();

    int getDistance();

    ISoundFormat getSoundFormat();

    IPlasmoVoiceSoundController setDistance(int distance);


    /**
     * @return boolean. Resolves can player that creates that sound hear source.
     */
    boolean canHearSource();

    /**
     * You can set when player can hear himself playing music or not.
     *
     * @param canHear - can player hear playing music.
     */
    void setHearSource(boolean canHear);

    /**
     * @return boolean. Disable music on player leave.
     */
    boolean isTurnOffOnLeave();

    /**
     * @param turnOffOnLeave - Disable music on player leave.
     */
    void setTurnOffOnLeave(boolean turnOffOnLeave);

    static IPlasmoVoiceSoundController of(ISoundFormat soundFormat, int distance) {
        return new DefaultPlasmoVoiceSoundController(soundFormat, distance);
    }
}
