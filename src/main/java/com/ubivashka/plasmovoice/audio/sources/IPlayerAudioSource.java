package com.ubivashka.plasmovoice.audio.sources;

import java.util.UUID;

public interface IPlayerAudioSource extends IAudioSource {
    /**
     * @return UUID. Возвращает UUID игрока. Игрок может быть оффлайн
     */
    UUID getPlayerUniqueId();

    /**
     * @return boolean. Возвращает значение которое определяет может ли слышать музыку которую воспроизводит.
     */
    boolean canHearSource();

    /**
     * Данным методом можно выбрать, слышит ли игрок музыку которую воспроизводит.
     *
     * @param canHear - Может ли слышать игрок музыку которую воспроизводит.
     */
    void setHearSource(boolean canHear);

    /**
     * @return boolean. Проигрывать ли музыку после перезахода игрока
     */
    boolean isTurnOffOnLeave();

    /**
     * @param turnOffOnLeave - Решает проигрывать ли музыку после того как игрок перезайдет на сервер
     */
    void setTurnOffOnLeave(boolean turnOffOnLeave);
}
