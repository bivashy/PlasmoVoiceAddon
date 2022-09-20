package com.ubivashka.plasmovoice.event;

import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.event.FileSoundPreResolveEvent.FileSoundResolveModel;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class FileSoundPlayEvent extends SoundPlayEvent {
    public FileSoundPlayEvent(FileSoundResolveModel resolveModel, ISoundFormat soundFormat,
            ISoundController soundController,
            ISoundPlaySession soundPlaySession) {
        super(resolveModel, soundFormat, soundController, soundPlaySession);
    }

    public FileSoundResolveModel getFileSoundResolveModel() {
        return (FileSoundResolveModel) getResolveModel();
    }
}
