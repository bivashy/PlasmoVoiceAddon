package com.ubivashka.plasmovoice.commands.argument;

public class SoundDistance {
    private final int distance;

    public SoundDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Returns default value if "distance" value is less than 0, or else returns "distance"
     *
     * @param defaultValue - default value
     */
    public int getValue(int defaultValue) {
        return distance < 0 ? defaultValue : distance;
    }
}
