package com.ubivashka.plasmovoice.commands;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.controller.PlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.commands.annotations.PluginsFolder;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.progress.InputStreamProgressWrapper;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.SoundBuilder;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

@Command("music")
public class MusicCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private PluginConfig config;

    @Subcommand("file")
    public void executeFileSubcommand(Player player, @PluginsFolder File file, @Default("100") @Flag("distance") int distance) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                Path path = file.toPath();
                playInputStream(player, Files.newInputStream(path), Files.size(path), distance);
            } catch(IOException e) {
                player.sendMessage(config.getMessages().getMessage("error-occurred"));
                e.printStackTrace();
            }
        });
    }

    @Subcommand("force file")
    public void executeFileSubcommand(BukkitCommandActor actor, Player player, @PluginsFolder File file, @Default("100") @Flag("distance") int distance) {
        executeFileSubcommand(player, file, distance);
    }


    @Subcommand("reload")
    @CommandPermission("plasmo.addon.reload")
    public void executeReloadSubcommand(BukkitCommandActor actor) {
        plugin.reload();
        actor.reply(config.getMessages().getMessage("config-reload"));
    }

    @Subcommand("url")
    public void executeUrlSubcommand(Player player, URL musicUrl, @Default("100") @Flag("distance") int distance) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URLConnection connection = musicUrl.openConnection();
                playInputStream(player, connection.getInputStream(), connection.getContentLengthLong(), distance);
            } catch(IOException e) {
                player.sendMessage(config.getMessages().getMessage("error-occurred"));
                e.printStackTrace();
            }
        });
    }

    @Subcommand("force url")
    @CommandPermission("plasmo.addon.url")
    public void executeUrlSubcommand(BukkitCommandActor actor, Player player, URL musicUrl, @Default("100") @Flag("distance") int distance) {
        executeUrlSubcommand(player, musicUrl, distance);
    }

    @Command("musicurl")
    public void executeUrlCommand(Player player, URL musicUrl, @Default("100") @Flag("distance") int distance) {
        executeUrlSubcommand(player, musicUrl, distance);
    }

    @Command("musicforceurl")
    @CommandPermission("plasmo.addon.url")
    public void executeUrlCommand(BukkitCommandActor actor, Player player, URL musicUrl, @Default("100") @Flag("distance") int distance) {
        executeUrlSubcommand(player, musicUrl, distance);
    }

    @Command("musicfile")
    public void executeFileCommand(Player player, @PluginsFolder File file, @Default("100") @Flag("distance") int distance) {
        executeFileSubcommand(player, file, distance);
    }

    @Command("musicforcefile")
    @CommandPermission("plasmo.addon.file")
    public void executeFileCommand(BukkitCommandActor actor, Player player, @PluginsFolder File file, @Default("100") @Flag("distance") int distance) {
        executeFileSubcommand(player, file, distance);
    }

    @Command("musicreload")
    public void executeReloadCommand(BukkitCommandActor actor) {
        executeReloadSubcommand(actor);
    }

    private void playInputStream(Player player, InputStream soundStream, long contentSize, int distance) {
        BossBar bossBar = plugin.getPluginConfig().getBossbarConfiguration().createBossbar();
        try {
            if (bossBar != null) {
                bossBar.addPlayer(player);
                soundStream = new InputStreamProgressWrapper(soundStream, contentSize).addListener(bossBar::setProgress);
            }
            ISound sound = new SoundBuilder(soundStream).build();

            if (sound == null) {
                player.sendMessage(config.getMessages().getMessage("cannot-create-sound"));
                return;
            }

            if (bossBar != null)
                bossBar.removeAll();
            PlayerAudioSource playerAudioSource = new PlayerAudioSource(player.getUniqueId(), plugin.getPlasmoVoiceSoundPlayer());
            playerAudioSource.sendAudioData(sound, new PlasmoVoiceSoundController(sound.getSoundFormat(), distance));
        } catch(UnsupportedAudioFileException |
                IOException e) {
            if (bossBar != null)
                bossBar.removeAll();
            player.sendMessage(config.getMessages().getMessage("error-occurred"));
            e.printStackTrace();
        }
    }
}
