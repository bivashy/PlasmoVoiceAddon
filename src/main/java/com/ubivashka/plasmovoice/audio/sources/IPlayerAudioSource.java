package com.ubivashka.plasmovoice.audio.sources;

import java.util.UUID;

public interface IPlayerAudioSource extends IAudioSource{
	/**
	 * @return UUID. Возвращает UUID игрока. Игрок может быть оффлайн
	 */
	public UUID getPlayerUniqueId();
	
	/**
	 * @return boolean. Возвращает значение которое определяет может ли слышать музыку которую воспроизводит.
	 */
	public boolean canHearSource();
	
	/**
	 * Данным методом можно выбрать, слышит ли игрок музыку которую воспроизводит.
	 * @param canHear - Может ли слышать игрок музыку которую воспроизводит.
	 */
	public void setHearSource(boolean canHear);
	
	/**
	 * @return boolean. Проигрывать ли музыку после перезахода игрока
	 */
	public boolean isTurnOffOnLeave();
	
	/**
	 * @param turnOffOnLeave - Решает проигрывать ли музыку после того как игрок перезайдет на сервер
	 */
	public void setTurnOffOnLeave(boolean turnOffOnLeave);
}
