package com.ubivashka.plasmovoice.audio.sources;

import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.ISoundPlaySession;
import com.ubivashka.plasmovoice.sound.ISound;

public interface IAudioSource {
    /**
     * Отправка данных звука в сжатом виде с помощью Opus. Если у вас файл, то нужно
     * отправлять разделяя аудио файл (в данный момент только wav) Смотреть
     * подробнее об разделении звука wav файла:
     * <a href="https://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java">https://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java</a>
     *
     * @param sound           - Итоговый полный звук сжатый с помощью Opus
     * @param soundController - Контроллер плеера, контролирует настройку музыки и прочее.
     * @return session of the sound that will need for controlling playing sound
     */
    ISoundPlaySession sendAudioData(ISound sound, ISoundController soundController);

    /**
     * Last session that was sent with method {@link IAudioSource#sendAudioData(ISound, ISoundController)}}
     *
     * @return Last sent session, may be null.
     */
    ISoundPlaySession getLastSession();
}
