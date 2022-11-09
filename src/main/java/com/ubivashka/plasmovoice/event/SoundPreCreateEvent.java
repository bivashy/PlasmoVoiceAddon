package com.ubivashka.plasmovoice.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ubivashka.plasmovoice.sound.ISound;

public class SoundPreCreateEvent<T> extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SoundEventModel<T> soundEventModel;
    private ISound sound;

    public SoundPreCreateEvent(SoundEventModel<T> soundEventModel) {
        super(true);
        this.soundEventModel = soundEventModel;
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
