package com.bivashy.plasmovoice.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.bivashy.plasmovoice.sound.ISoundFormat;

public class SoundFormatPreCreateEvent<T> extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SoundEventModel<T> soundEventModel;
    private ISoundFormat soundFormat;

    public SoundFormatPreCreateEvent(SoundEventModel<T> soundEventModel) {
        super(true);
        this.soundEventModel = soundEventModel;
    }

    public SoundEventModel<T> getSoundEventModel() {
        return soundEventModel;
    }

    public ISoundFormat getSoundFormat() {
        return soundFormat;
    }

    public void setSoundFormat(ISoundFormat soundFormat) {
        this.soundFormat = soundFormat;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
