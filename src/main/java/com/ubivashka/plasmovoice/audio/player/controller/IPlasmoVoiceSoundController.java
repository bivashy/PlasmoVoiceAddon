package com.ubivashka.plasmovoice.audio.player.controller;

import com.ubivashka.plasmovoice.config.settings.MusicPlayerSettings;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public interface IPlasmoVoiceSoundController extends ISoundController {
    MusicPlayerSettings getMusicPlayerSettings();

    int getDistance();

    ISoundFormat getSoundFormat();

    IPlasmoVoiceSoundController setDistance(int distance);

    static IPlasmoVoiceSoundController of(ISoundFormat soundFormat, int distance) {
        return new DefaultPlasmoVoiceSoundController(soundFormat, distance);
    }
}
