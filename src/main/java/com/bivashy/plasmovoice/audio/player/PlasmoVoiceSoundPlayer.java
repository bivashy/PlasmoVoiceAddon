package com.bivashy.plasmovoice.audio.player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.bivashy.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.bivashy.plasmovoice.audio.player.controller.ISoundController;
import com.bivashy.plasmovoice.audio.player.session.ISoundPlaySession;
import com.bivashy.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.bivashy.plasmovoice.audio.sources.IPlayerAudioSource;
import com.bivashy.plasmovoice.audio.sources.PlayerAudioSource;
import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPreResolveEvent;
import com.bivashy.plasmovoice.event.factory.SoundEventsFactory;
import com.bivashy.plasmovoice.sound.ISound;
import com.bivashy.plasmovoice.sound.ISoundFormat;

public class PlasmoVoiceSoundPlayer implements ISoundPlayer {
    private final Deque<PlasmoVoiceSoundPlaySession> sessionsDeque = new ArrayDeque<>();
    private final IPlayerAudioSource playerAudioSource;

    public PlasmoVoiceSoundPlayer(IPlayerAudioSource playerAudioSource) {
        this.playerAudioSource = playerAudioSource;
    }

    public PlasmoVoiceSoundPlayer(UUID uuid) {
        this(new PlayerAudioSource(uuid));
    }

    @Override
    public PlasmoVoiceSoundPlaySession playSound(ISound sound, ISoundController soundController) {
        if (!(soundController instanceof IPlasmoVoiceSoundController))
            return null;
        PlasmoVoiceSoundPlaySession soundPlaySession = new PlasmoVoiceSoundPlaySession(sound, this, (IPlasmoVoiceSoundController) soundController);
        if (!playerAudioSource.getCurrentSession().map(ISoundPlaySession::isEnded).orElse(true)) {
            sessionsDeque.add(soundPlaySession);
            return soundPlaySession;
        }
        soundPlaySession.playSound();
        playerAudioSource.setCurrentSession(soundPlaySession);
        return soundPlaySession;
    }

    public Optional<PlasmoVoiceSoundPlaySession> playSound(URL url, InputStream urlStream) throws IOException {
        return playSound(url, urlStream, SoundEventsFactory.URL_FACTORY);
    }


    public Optional<PlasmoVoiceSoundPlaySession> playSound(File file, InputStream fileStream) throws IOException {
        return playSound(file, fileStream, SoundEventsFactory.FILE_FACTORY);
    }

    private <T> Optional<PlasmoVoiceSoundPlaySession> playSound(T source, InputStream sourceStream, SoundEventsFactory<T> eventsFactory) throws IOException {
        SoundEventModel<T> soundEventModel = new SoundEventModel<>(this, playerAudioSource, sourceStream, source);
        SoundPreResolveEvent<T> preResolveEvent = eventsFactory.createPreResolveEvent(soundEventModel);
        Bukkit.getPluginManager().callEvent(preResolveEvent);
        Optional<ISoundFormat> soundFormatOptional = eventsFactory.createSoundFormat(soundEventModel);

        Optional<PlasmoVoiceSoundPlaySession> soundPlaySessionOptional
                = soundFormatOptional.map(soundFormat -> {
            Bukkit.getPluginManager().callEvent(eventsFactory.createSoundPrePlayEvent(soundEventModel, soundFormat));
            IPlasmoVoiceSoundController soundController = IPlasmoVoiceSoundController.of(soundFormat, soundFormat.getSettings().getDistance());
            PlasmoVoiceSoundPlaySession soundPlaySession = playSound(
                    eventsFactory.createSound(soundFormat, soundEventModel), soundController);
            Bukkit.getPluginManager().callEvent(eventsFactory.createSoundPlayEvent(soundEventModel, soundFormat, soundController, soundPlaySession));
            return soundPlaySession;
        });
        if (!soundFormatOptional.isPresent())
            soundEventModel.getInputStream().close();
        return soundPlaySessionOptional;
    }

    @Override
    public IPlayerAudioSource getSource() {
        return playerAudioSource;
    }

    public Collection<PlasmoVoiceSoundPlaySession> getSessions() {
        return Collections.unmodifiableCollection(sessionsDeque);
    }

    public boolean tryPlayNextSound() {
        if (!playerAudioSource.getCurrentSession().map(ISoundPlaySession::isEnded).orElse(true))
            return false;
        PlasmoVoiceSoundPlaySession session = sessionsDeque.pollFirst();
        if (session == null)
            return false;
        playerAudioSource.setCurrentSession(session);
        session.playSound();
        return true;
    }

    public boolean forceNextSound() {
        playerAudioSource.getCurrentSession().ifPresent(ISoundPlaySession::end);
        PlasmoVoiceSoundPlaySession session = sessionsDeque.pollFirst();
        if (session == null)
            return false;
        playerAudioSource.setCurrentSession(session);
        session.playSound();
        return true;
    }
}
