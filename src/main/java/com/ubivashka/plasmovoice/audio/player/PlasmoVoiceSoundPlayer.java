package com.ubivashka.plasmovoice.audio.player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.player.controller.ISoundController;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.audio.sources.IAudioSource;
import com.ubivashka.plasmovoice.audio.sources.IPlayerAudioSource;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.event.FileSoundPlayEvent;
import com.ubivashka.plasmovoice.event.FileSoundPreResolveEvent;
import com.ubivashka.plasmovoice.event.FileSoundPreResolveEvent.FileSoundResolveModel;
import com.ubivashka.plasmovoice.event.URLSoundPlayEvent;
import com.ubivashka.plasmovoice.event.URLSoundPreResolveEvent;
import com.ubivashka.plasmovoice.event.URLSoundPreResolveEvent.URLSoundResolveModel;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public class PlasmoVoiceSoundPlayer implements ISoundPlayer {
    private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
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
        PlasmoVoiceSoundPlaySession soundPlaySession = new PlasmoVoiceSoundPlaySession(sound, playerAudioSource, (IPlasmoVoiceSoundController) soundController);
        playerAudioSource.setLastSession(soundPlaySession);
        return soundPlaySession;
    }

    public Optional<PlasmoVoiceSoundPlaySession> playSound(URL url, InputStream urlStream) throws IOException {
        URLSoundResolveModel resolveModel = new URLSoundResolveModel(this, playerAudioSource, url, urlStream);
        URLSoundPreResolveEvent preResolveEvent = new URLSoundPreResolveEvent(resolveModel);
        Bukkit.getPluginManager().callEvent(preResolveEvent);
        Optional<ISoundFormat> soundFormatOptional = PLUGIN.getSoundFormatHolder().findFirstFormat(resolveModel.getUrl(), resolveModel.getInputStream());
        Optional<PlasmoVoiceSoundPlaySession> soundPlaySessionOptional = soundFormatOptional.map(soundFormat -> {
            IPlasmoVoiceSoundController soundController = IPlasmoVoiceSoundController.of(soundFormat, soundFormat.getSettings().getDistance());
            PlasmoVoiceSoundPlaySession soundPlaySession = playSound(
                    soundFormat.newSoundFactory().createSound(resolveModel.getUrl(), resolveModel.getInputStream()), soundController);
            Bukkit.getPluginManager().callEvent(new URLSoundPlayEvent(resolveModel, soundFormat, soundController, soundPlaySession));
            return soundPlaySession;
        });
        if (!soundFormatOptional.isPresent())
            resolveModel.getInputStream().close();
        return soundPlaySessionOptional;
    }


    public Optional<PlasmoVoiceSoundPlaySession> playSound(File file, InputStream fileStream) throws IOException {
        FileSoundResolveModel resolveModel = new FileSoundResolveModel(this, playerAudioSource, file, fileStream);
        FileSoundPreResolveEvent preResolveEvent = new FileSoundPreResolveEvent(resolveModel);
        Bukkit.getPluginManager().callEvent(preResolveEvent);
        Optional<ISoundFormat> soundFormatOptional = PLUGIN.getSoundFormatHolder().findFirstFormat(resolveModel.getFile(), resolveModel.getInputStream());
        Optional<PlasmoVoiceSoundPlaySession> soundPlaySessionOptional = soundFormatOptional.map(soundFormat -> {
            IPlasmoVoiceSoundController soundController = IPlasmoVoiceSoundController.of(soundFormat, soundFormat.getSettings().getDistance());
            PlasmoVoiceSoundPlaySession soundPlaySession = playSound(
                    soundFormat.newSoundFactory().createSound(resolveModel.getFile(), resolveModel.getInputStream()), soundController);
            Bukkit.getPluginManager().callEvent(new FileSoundPlayEvent(resolveModel, soundFormat, soundController, soundPlaySession));
            return soundPlaySession;
        });
        if (!soundFormatOptional.isPresent())
            resolveModel.getInputStream().close();
        return soundPlaySessionOptional;
    }


    @Override
    public IAudioSource getSource() {
        return playerAudioSource;
    }
}
