package com.bivashy.plasmovoice.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.bivashy.plasmovoice.sound.ISound;

public class SoundCreateEvent<T> extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SoundEventModel<T> soundEventModel;
    private ISound sound;

    public SoundCreateEvent(SoundEventModel<T> soundEventModel, ISound sound) {
        super(true);
        this.soundEventModel = soundEventModel;
        this.sound = sound;
    }

    public SoundEventModel<T> getSoundEventModel() {
        return soundEventModel;
    }

    public ISound getSound() {
        return sound;
    }

    public void setSound(ISound sound) {
        this.sound = sound;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
