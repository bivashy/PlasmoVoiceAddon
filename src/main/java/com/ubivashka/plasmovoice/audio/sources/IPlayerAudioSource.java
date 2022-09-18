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

    /**
     * @return boolean. Resolves can player that creates that sound hear source.
     */
    boolean canHearSource();

    /**
     * You can set when player can hear himself playing music or not.
     *
     * @param canHear - can player hear playing music.
     */
    void setHearSource(boolean canHear);

    /**
     * @return boolean. Disable music on player leave.
     */
    boolean isTurnOffOnLeave();

    /**
     * @param turnOffOnLeave - Disable music on player leave.
     */
    void setTurnOffOnLeave(boolean turnOffOnLeave);
}
