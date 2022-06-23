package com.ubivashka.plasmovoice.sound.util;

public class VolumeAdjuster {
    private final CircularFloatBuffer highestValues = new CircularFloatBuffer(48, -1F);

    public byte[] adjust(byte[] samples, float targetVolume) {
        if (targetVolume == 1)
            return samples;
        short[] shorts = AudioUtils.bytesToShorts(samples);
        return AudioUtils.shortsToBytes(adjust(shorts, targetVolume));
    }

    public short[] adjust(short[] samples, float targetVolume) {
        if (targetVolume == 1)
            return samples;
        short highestValue = getHighestAbsoluteSample(samples);
        float highestPossibleMultiplier = (float) (Short.MAX_VALUE - 1) / (float) highestValue;
        if (targetVolume > highestPossibleMultiplier) {
            targetVolume = highestPossibleMultiplier;
        }

        highestValues.put(targetVolume);


        float minVolume = -1F;
        for (float highest : highestValues.getBuffer()) {
            if (highest < 0F) {
                continue;
            }

            if (minVolume < 0F) {
                minVolume = highest;
                continue;
            }

            if (highest < minVolume) {
                minVolume = highest;
            }
        }

        targetVolume = Math.min(minVolume, targetVolume);

        for (int i = 0; i < samples.length; i++) {
            samples[i] *= targetVolume;
        }
        return samples;
    }

    private short getHighestAbsoluteSample(short[] samples) {
        short max = 0;
        for (short sample : samples) {
            if (sample == Short.MIN_VALUE) {
                sample += 1;
            }

            sample = (short) Math.abs(sample);
            if (sample > max) {
                max = sample;
            }
        }

        return max;
    }
}