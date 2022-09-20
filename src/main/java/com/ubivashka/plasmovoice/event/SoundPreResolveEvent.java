package com.ubivashka.plasmovoice.event;

import java.io.InputStream;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ubivashka.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;

public class SoundPreResolveEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SoundResolveModel soundResolveModel;

    public SoundPreResolveEvent(SoundResolveModel soundResolveModel) {
        super(true);
        this.soundResolveModel = soundResolveModel;
    }

    public SoundResolveModel getSoundResolveModel() {
        return soundResolveModel;
    }

    public static class SoundResolveModel {
        private PlasmoVoiceSoundPlayer soundPlayer;
        private IPlayerAudioSource playerAudioSource;
        private InputStream inputStream;

        public SoundResolveModel(PlasmoVoiceSoundPlayer soundPlayer, IPlayerAudioSource playerAudioSource, InputStream inputStream) {
            this.soundPlayer = soundPlayer;
            this.playerAudioSource = playerAudioSource;
            this.inputStream = inputStream;
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
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
