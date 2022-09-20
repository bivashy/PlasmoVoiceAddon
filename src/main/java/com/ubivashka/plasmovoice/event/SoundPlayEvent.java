package com.ubivashka.plasmovoice.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.event.SoundPreResolveEvent.SoundResolveModel;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class SoundPlayEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SoundResolveModel resolveModel;
    private final ISoundFormat soundFormat;
    private final ISoundController soundController;
    private final ISoundPlaySession soundPlaySession;

    public SoundPlayEvent(SoundResolveModel resolveModel, ISoundFormat soundFormat, ISoundController soundController,
            ISoundPlaySession soundPlaySession) {
        super(true);
        this.resolveModel = resolveModel;
        this.soundFormat = soundFormat;
        this.soundController = soundController;
        this.soundPlaySession = soundPlaySession;
    }

    public SoundResolveModel getResolveModel() {
        return resolveModel;
    }

    public ISoundFormat getSoundFormat() {
        return soundFormat;
    }

    public ISoundController getSoundController() {
        return soundController;
    }

    public ISoundPlaySession getSoundPlaySession() {
        return soundPlaySession;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
