package com.ubivashka.plasmovoice.audio.player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.ubivashka.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.event.SoundPreResolveEvent;
import com.ubivashka.plasmovoice.event.factory.SoundEventsFactory;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

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
        if (!playerAudioSource.getLastSession().isEnded()) {
            sessionsDeque.add(soundPlaySession);
            return soundPlaySession;
        }
        soundPlaySession.playSound();
        playerAudioSource.setLastSession(soundPlaySession);
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

    public boolean tryPlayNextSound() {
        if (playerAudioSource.getLastSession()==null || !playerAudioSource.getLastSession().isEnded())
            return false;
        PlasmoVoiceSoundPlaySession session = sessionsDeque.pollFirst();
        if (session == null)
            return false;
        playerAudioSource.setLastSession(session);
        session.playSound();
        return true;
    }

    public boolean forceNextSound() {
        if(playerAudioSource.getLastSession()!=null)
            playerAudioSource.getLastSession().end();
        PlasmoVoiceSoundPlaySession session = sessionsDeque.pollFirst();
        if (session == null)
            return false;
        playerAudioSource.setLastSession(session);
        session.playSound();
        return true;
    }
}
