package com.ubivashka.plasmovoice.audio.player;

import com.ubivashka.plasmovoice.audio.codecs.ICodecHolder;
import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.audio.sources.IAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;

public interface ISoundPlayer {

    /**
     * @param sound            - Проигрываемый звук
     * @param audioSource      - Источник звука
     * @param soundController  - Контроллер плеера, контролирует настройку музыки и прочее.
     * @return {@link ISoundPlaySession} - Сессия звука, можно отменить проигрывание
     * звука при надобности
     */
    ISoundPlaySession playSound(ISound sound, IAudioSource audioSource, ISoundController soundController);

    /**
     * @return Создаёт кодек с которым можно сжимать и обратить этот процесс.
     */
    ICodecHolder createCodecHolder();
}
