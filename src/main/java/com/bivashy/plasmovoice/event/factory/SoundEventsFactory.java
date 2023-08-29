package com.bivashy.plasmovoice.event.factory;

import java.io.File;
import java.net.URL;
import java.util.Optional;

import org.bukkit.Bukkit;

import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.bivashy.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.bivashy.plasmovoice.event.SoundEventModel;
import com.bivashy.plasmovoice.event.SoundPlayEvent;
import com.bivashy.plasmovoice.event.SoundPrePlayEvent;
import com.bivashy.plasmovoice.event.SoundPreResolveEvent;
import com.bivashy.plasmovoice.event.file.FileSoundCreateEvent;
import com.bivashy.plasmovoice.event.file.FileSoundFormatPreCreateEvent;
import com.bivashy.plasmovoice.event.file.FileSoundPlayEvent;
import com.bivashy.plasmovoice.event.file.FileSoundPreCreateEvent;
import com.bivashy.plasmovoice.event.file.FileSoundPrePlayEvent;
import com.bivashy.plasmovoice.event.file.FileSoundPreResolveEvent;
import com.bivashy.plasmovoice.event.url.URLSoundCreateEvent;
import com.bivashy.plasmovoice.event.url.URLSoundFormatPreCreateEvent;
import com.bivashy.plasmovoice.event.url.URLSoundPlayEvent;
import com.bivashy.plasmovoice.event.url.URLSoundPreCreateEvent;
import com.bivashy.plasmovoice.event.url.URLSoundPrePlayEvent;
import com.bivashy.plasmovoice.event.url.URLSoundPreResolveEvent;
import com.bivashy.plasmovoice.sound.ISound;
import com.bivashy.plasmovoice.sound.ISoundFormat;

public interface SoundEventsFactory<T> {
    SoundEventsFactory<URL> URL_FACTORY = new SoundEventsFactory<URL>() {
        private final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);

        @Override
        public SoundPreResolveEvent<URL> createPreResolveEvent(SoundEventModel<URL> soundEventModel) {
            return new URLSoundPreResolveEvent(soundEventModel);
        }

        @Override
        public Optional<ISoundFormat> createSoundFormat(SoundEventModel<URL> soundEventModel) {
            URLSoundFormatPreCreateEvent soundFormatPreCreateEvent = new URLSoundFormatPreCreateEvent(soundEventModel);
            Bukkit.getPluginManager().callEvent(soundFormatPreCreateEvent);
            if (soundFormatPreCreateEvent.getSoundFormat() != null)
                return Optional.of(soundFormatPreCreateEvent.getSoundFormat());
            return PLUGIN.getSoundFormatHolder().findFirstFormat(soundEventModel.getSource(), soundEventModel.getInputStream());
        }

        @Override
        public SoundPrePlayEvent<URL> createSoundPrePlayEvent(SoundEventModel<URL> soundEventModel, ISoundFormat soundFormat) {
            return new URLSoundPrePlayEvent(soundEventModel, soundFormat);
        }

        @Override
        public ISound createSound(ISoundFormat soundFormat, SoundEventModel<URL> soundEventModel) {
            URLSoundPreCreateEvent soundPreCreateEvent = new URLSoundPreCreateEvent(soundEventModel);
            Bukkit.getPluginManager().callEvent(soundPreCreateEvent);
            if (soundPreCreateEvent.getSound() != null)
                return soundPreCreateEvent.getSound();
            ISound sound = soundFormat.newSoundFactory().createSound(soundEventModel.getSource(), soundEventModel.getInputStream());
            URLSoundCreateEvent soundCreateEvent = new URLSoundCreateEvent(soundEventModel, sound);
            Bukkit.getPluginManager().callEvent(soundCreateEvent);
            return soundCreateEvent.getSound();
        }

        @Override
        public SoundPlayEvent<URL> createSoundPlayEvent(SoundEventModel<URL> soundEventModel, ISoundFormat soundFormat,
                                                        IPlasmoVoiceSoundController soundController, PlasmoVoiceSoundPlaySession soundPlaySession) {
            return new URLSoundPlayEvent(soundEventModel, soundFormat, soundController, soundPlaySession);
        }
    };
    SoundEventsFactory<File> FILE_FACTORY = new SoundEventsFactory<File>() {
        private final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);

        @Override
        public SoundPreResolveEvent<File> createPreResolveEvent(SoundEventModel<File> soundEventModel) {
            return new FileSoundPreResolveEvent(soundEventModel);
        }

        @Override
        public Optional<ISoundFormat> createSoundFormat(SoundEventModel<File> soundEventModel) {
            FileSoundFormatPreCreateEvent soundFormatPreCreateEvent = new FileSoundFormatPreCreateEvent(soundEventModel);
            Bukkit.getPluginManager().callEvent(soundFormatPreCreateEvent);
            if (soundFormatPreCreateEvent.getSoundFormat() != null)
                return Optional.of(soundFormatPreCreateEvent.getSoundFormat());
            return PLUGIN.getSoundFormatHolder().findFirstFormat(soundEventModel.getSource(), soundEventModel.getInputStream());
        }

        @Override
        public SoundPrePlayEvent<File> createSoundPrePlayEvent(SoundEventModel<File> soundEventModel, ISoundFormat soundFormat) {
            return new FileSoundPrePlayEvent(soundEventModel, soundFormat);
        }

        @Override
        public ISound createSound(ISoundFormat soundFormat, SoundEventModel<File> soundEventModel) {
            FileSoundPreCreateEvent soundPreCreateEvent = new FileSoundPreCreateEvent(soundEventModel);
            Bukkit.getPluginManager().callEvent(soundPreCreateEvent);
            if (soundPreCreateEvent.getSound() != null)
                return soundPreCreateEvent.getSound();
            ISound sound = soundFormat.newSoundFactory().createSound(soundEventModel.getSource(), soundEventModel.getInputStream());
            FileSoundCreateEvent soundCreateEvent = new FileSoundCreateEvent(soundEventModel, sound);
            Bukkit.getPluginManager().callEvent(soundCreateEvent);
            return soundCreateEvent.getSound();
        }

        @Override
        public SoundPlayEvent<File> createSoundPlayEvent(SoundEventModel<File> soundEventModel, ISoundFormat soundFormat,
                IPlasmoVoiceSoundController soundController, PlasmoVoiceSoundPlaySession soundPlaySession) {
            return new FileSoundPlayEvent(soundEventModel, soundFormat, soundController, soundPlaySession);
        }
    };


    SoundPreResolveEvent<T> createPreResolveEvent(SoundEventModel<T> soundEventModel);

    Optional<ISoundFormat> createSoundFormat(SoundEventModel<T> soundEventModel);

    SoundPrePlayEvent<T> createSoundPrePlayEvent(SoundEventModel<T> soundEventModel, ISoundFormat soundFormat);

    SoundPlayEvent<T> createSoundPlayEvent(SoundEventModel<T> soundEventModel, ISoundFormat soundFormat, IPlasmoVoiceSoundController soundController,
            PlasmoVoiceSoundPlaySession soundPlaySession);

    ISound createSound(ISoundFormat soundFormat, SoundEventModel<T> soundEventModel);
}
