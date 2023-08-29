package com.bivashy.plasmovoice.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.bivashy.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;

public class SoundSessionEndEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final PlasmoVoiceSoundPlaySession session;

    public SoundSessionEndEvent(PlasmoVoiceSoundPlaySession session) {
        this.session = session;
    }

    public PlasmoVoiceSoundPlaySession getSession() {
        return session;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
