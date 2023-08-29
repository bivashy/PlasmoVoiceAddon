package com.bivashy.plasmovoice.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SoundPreResolveEvent<T> extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SoundEventModel<T> soundEventModel;

    public SoundPreResolveEvent(SoundEventModel<T> soundEventModel) {
        super(true);
        this.soundEventModel = soundEventModel;
    }

    public SoundEventModel<T> getSoundEventModel() {
        return soundEventModel;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
