package com.bivashy.plasmovoice.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.bivashy.plasmovoice.audio.player.controller.ISoundController;
import com.bivashy.plasmovoice.audio.player.session.ISoundPlaySession;
import com.bivashy.plasmovoice.sound.ISoundFormat;

public class SoundPlayEvent<T> extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SoundEventModel<T> soundEventModel;
    private final ISoundFormat soundFormat;
    private final ISoundController soundController;
    private final ISoundPlaySession soundPlaySession;

    public SoundPlayEvent(SoundEventModel<T> soundEventModel, ISoundFormat soundFormat, ISoundController soundController,
            ISoundPlaySession soundPlaySession) {
        super(true);
        this.soundEventModel = soundEventModel;
        this.soundFormat = soundFormat;
        this.soundController = soundController;
        this.soundPlaySession = soundPlaySession;
    }

    public SoundEventModel<T> getSoundEventModel() {
        return soundEventModel;
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
