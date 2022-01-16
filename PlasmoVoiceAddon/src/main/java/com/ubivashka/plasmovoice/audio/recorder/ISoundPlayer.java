package com.ubivashka.plasmovoice.audio.recorder;

import com.ubivashka.plasmovoice.audio.codecs.ICodecHolder;
import com.ubivashka.plasmovoice.audio.sources.IAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;

public interface ISoundPlayer {

	/**
	 * @param sound       - Проигрываемый звук
	 * @param distance    - Дистанция проигрываемого звука
	 * @param audioSource - Источник звука
	 * @return {@link ISoundPlaySession} - Сессия звука, можно отменить проигрывание
	 *         звука при надобности
	 */
	public ISoundPlaySession playSound(ISound sound, int distance, IAudioSource audioSource);

	/**
	 * @return Возвращает кодек с которым можно сжимать и обратить этот процесс.
	 */
	public ICodecHolder getCodecHolder();
}
