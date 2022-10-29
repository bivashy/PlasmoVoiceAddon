package com.ubivashka.plasmovoice.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ubivashka.plasmovoice.event.SoundSessionEndEvent;

public class SoundSessionListener implements Listener {
    @EventHandler
    public void onSessionEnd(SoundSessionEndEvent e) {
        e.getSession().getSoundPlayer().tryPlayNextSound();
    }
}
