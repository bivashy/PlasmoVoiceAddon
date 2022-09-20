package com.ubivashka.plasmovoice.event;

import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.event.URLSoundPreResolveEvent.URLSoundResolveModel;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class URLSoundPlayEvent extends SoundPlayEvent {
    public URLSoundPlayEvent(URLSoundResolveModel resolveModel, ISoundFormat soundFormat, ISoundController soundController,
            ISoundPlaySession soundPlaySession) {
        super(resolveModel, soundFormat, soundController, soundPlaySession);
    }

    public URLSoundResolveModel getUrlSoundResolveModel() {
        return (URLSoundResolveModel) getResolveModel();
    }
}
