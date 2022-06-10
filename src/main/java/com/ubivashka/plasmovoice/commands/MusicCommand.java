package com.ubivashka.plasmovoice.commands;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.controller.PlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.commands.annotations.PluginsFolder;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.SoundBuilder;
import org.bukkit.Bukkit;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.BukkitCommandActor;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

@Command("music")
public class MusicCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private PluginConfig config;

    @Subcommand("file")
    public void executeFileSubcommand(BukkitCommandActor actor, @PluginsFolder File file, @Default("100") @Flag("distance") int distance) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                playInputStream(actor, Files.newInputStream(file.toPath()), distance);
            } catch(IOException e) {
                actor.reply(config.getMessages().getMessage("error-occurred"));
                e.printStackTrace();
            }
        });
    }


    @Subcommand("reload")
    public void executeReloadSubcommand(BukkitCommandActor actor) {
        plugin.reload();
        actor.reply(config.getMessages().getMessage("config-reload"));
    }

    @Subcommand("url")
    public void executeUrlSubcommand(BukkitCommandActor actor, URL musicUrl, @Default("100") @Flag("distance") int distance) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                playInputStream(actor, musicUrl.openStream(), distance);
            } catch(IOException e) {
                actor.reply(config.getMessages().getMessage("error-occurred"));
                e.printStackTrace();
            }
        });
    }

    @Command("musicurl")
    public void executeUrlCommand(BukkitCommandActor actor, URL musicUrl, @Default("100") @Flag("distance") int distance) {
        executeUrlSubcommand(actor, musicUrl, distance);
    }

    @Command("musicfile")
    public void executeFileCommand(BukkitCommandActor actor, @PluginsFolder File file, @Default("100") @Flag("distance") int distance) {
        executeFileSubcommand(actor, file, distance);
    }

    @Command("musicreload")
    public void executeReloadCommand(BukkitCommandActor actor) {
        executeReloadSubcommand(actor);
    }

    private void playInputStream(BukkitCommandActor actor, InputStream soundStream, int distance) {
        try {
            ISound sound = new SoundBuilder(soundStream).build();

            if (sound == null) {
                actor.reply(config.getMessages().getMessage("cannot-create-sound"));
                return;
            }

            PlayerAudioSource playerAudioSource = new PlayerAudioSource(actor.getUniqueId(), plugin.getPlasmoVoiceSoundPlayer());
            playerAudioSource.sendAudioData(sound, new PlasmoVoiceSoundController(sound.getSoundFormat(),distance));
        } catch(UnsupportedAudioFileException |
                IOException e) {
            actor.reply(config.getMessages().getMessage("error-occurred"));
            e.printStackTrace();
        }
    }
}
