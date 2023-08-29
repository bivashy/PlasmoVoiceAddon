package com.bivashy.plasmovoice.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.bivashy.plasmovoice.sound.ISoundFormat;

public class SoundPrePlayEvent<T> extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SoundEventModel<T> soundEventModel;
    private final ISoundFormat soundFormat;

    public SoundPrePlayEvent(SoundEventModel<T> soundEventModel, ISoundFormat soundFormat) {
        super(true);
        this.soundEventModel = soundEventModel;
        this.soundFormat = soundFormat;
    }

    public SoundEventModel<T> getSoundEventModel() {
        return soundEventModel;
    }

    public ISoundFormat getSoundFormat() {
        return soundFormat;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
