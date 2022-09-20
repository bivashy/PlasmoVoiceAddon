package com.ubivashka.plasmovoice.event;

import java.io.InputStream;
import java.net.URL;

import com.ubivashka.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;

public class URLSoundPreResolveEvent extends SoundPreResolveEvent {
    private final URLSoundResolveModel soundResolveModel;

    public URLSoundPreResolveEvent(URLSoundResolveModel soundResolveModel) {
        super(soundResolveModel);
        this.soundResolveModel = soundResolveModel;
    }

    public URLSoundResolveModel getSoundResolveModel() {
        return soundResolveModel;
    }

    public static class URLSoundResolveModel extends SoundResolveModel {
        private URL url;

        public URLSoundResolveModel(PlasmoVoiceSoundPlayer soundPlayer, IPlayerAudioSource playerAudioSource, URL url, InputStream inputStream) {
            super(soundPlayer, playerAudioSource, inputStream);
            this.url = url;
        }

        public URL getUrl() {
            return url;
        }

        public void setUrl(URL url) {
            this.url = url;
        }
    }
}
