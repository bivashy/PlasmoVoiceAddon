package com.ubivashka.plasmovoice.audio.sources;

import java.util.UUID;

import org.bukkit.Location;

public interface IPlayerAudioSource extends IAudioSource {
    /**
     * @return UUID. Returns player UUID. Player may be offline.
     */
    UUID getPlayerUniqueId();

    /**
     * @return Location. Location where sound will be played.
     */
    Location getPlayerLocation();
}
