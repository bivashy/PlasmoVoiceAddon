package com.ubivashka.plasmovoice.event.factory;

import java.io.File;
import java.net.URL;
import java.util.Optional;

import org.bukkit.Bukkit;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.event.SoundEventModel;
import com.ubivashka.plasmovoice.event.SoundPlayEvent;
import com.ubivashka.plasmovoice.event.SoundPrePlayEvent;
import com.ubivashka.plasmovoice.event.SoundPreResolveEvent;
import com.ubivashka.plasmovoice.event.file.FileSoundCreateEvent;
import com.ubivashka.plasmovoice.event.file.FileSoundPlayEvent;
import com.ubivashka.plasmovoice.event.file.FileSoundPrePlayEvent;
import com.ubivashka.plasmovoice.event.file.FileSoundPreResolveEvent;
import com.ubivashka.plasmovoice.event.url.URLSoundCreateEvent;
import com.ubivashka.plasmovoice.event.url.URLSoundPlayEvent;
import com.ubivashka.plasmovoice.event.url.URLSoundPrePlayEvent;
import com.ubivashka.plasmovoice.event.url.URLSoundPreResolveEvent;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

public interface SoundEventsFactory<T> {
    SoundEventsFactory<URL> URL_FACTORY = new SoundEventsFactory<URL>() {
        private final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);

        @Override
        public SoundPreResolveEvent<URL> createPreResolveEvent(SoundEventModel<URL> soundEventModel) {
            return new URLSoundPreResolveEvent(soundEventModel);
        }

        @Override
        public Optional<ISoundFormat> createSoundFormat(SoundEventModel<URL> soundEventModel) {
            return PLUGIN.getSoundFormatHolder().findFirstFormat(soundEventModel.getSource(), soundEventModel.getInputStream());
        }

        @Override
        public SoundPrePlayEvent<URL> createSoundPrePlayEvent(SoundEventModel<URL> soundEventModel, ISoundFormat soundFormat) {
            return new URLSoundPrePlayEvent(soundEventModel, soundFormat);
        }

        @Override
        public ISound createSound(ISoundFormat soundFormat, SoundEventModel<URL> soundEventModel) {
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
            return PLUGIN.getSoundFormatHolder().findFirstFormat(soundEventModel.getSource(), soundEventModel.getInputStream());
        }

        @Override
        public SoundPrePlayEvent<File> createSoundPrePlayEvent(SoundEventModel<File> soundEventModel, ISoundFormat soundFormat) {
            return new FileSoundPrePlayEvent(soundEventModel, soundFormat);
        }

        @Override
        public ISound createSound(ISoundFormat soundFormat, SoundEventModel<File> soundEventModel) {
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
