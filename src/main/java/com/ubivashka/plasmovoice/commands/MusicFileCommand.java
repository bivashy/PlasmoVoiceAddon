package com.ubivashka.plasmovoice.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.commands.annotations.PluginsFolder;
import com.ubivashka.plasmovoice.commands.argument.SoundDistance;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.config.settings.command.FileCommandSettings;
import com.ubivashka.plasmovoice.progress.InputStreamProgressWrapper;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Flag;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.exception.SendMessageException;

@Command("music")
public class MusicFileCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private PluginConfig config;
    @Dependency
    private FileCommandSettings settings;

    @Subcommand("file")
    public void executeFileSubcommand(Player player, @PluginsFolder File file, @Default("-1") @Flag("distance") SoundDistance distance) {
        if (!settings.isEnabled())
            throw new SendMessageException(config.getMessages().getMessage("file-command-disabled"));
        if (settings.getPermission().isPresent() && !player.hasPermission(settings.getPermission().get()))
            throw new SendMessageException(config.getMessages().getMessage("not-enough-permission"));
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Path path = file.toPath();
            try {
                long fileSize = Files.size(path);
                if (settings.getSizeLimit() > 0 && fileSize > settings.getSizeLimit()) {
                    player.sendMessage(config.getMessages().getMessage("invalid-size"));
                    return;
                }
                InputStream fileStream = new BufferedInputStream(createProgressStream(Files.newInputStream(path), Files.size(path), player));
                Optional<ISoundFormat> optionalSoundFormat =
                        plugin.getSoundFormatHolder().findFirstByPredicate(format -> format.isSupported(file, fileStream));
                if (!optionalSoundFormat.isPresent()) {
                    player.sendMessage(config.getMessages().getMessage("cannot-create-sound"));
                    fileStream.close();
                    return;
                }
                ISoundFormat soundFormat = optionalSoundFormat.get();

                PlayerAudioSource playerAudioSource = new PlayerAudioSource(player.getUniqueId(), plugin.getPlasmoVoiceSoundPlayer());
                playerAudioSource.sendAudioData(soundFormat.newSoundFactory().createSound(file, fileStream),
                        IPlasmoVoiceSoundController.of(soundFormat, distance.getValue(soundFormat.getSettings().getDistance())));
                fileStream.close();
            } catch(IOException e) {
                player.sendMessage(config.getMessages().getMessage("error-occurred"));
                e.printStackTrace();
            }
        });
    }

    @Subcommand("force file")
    @CommandPermission("plasmo.addon.file")
    public void executeFileSubcommand(BukkitCommandActor actor, Player player, @PluginsFolder File file,
            @Default("-1") @Flag("distance") SoundDistance distance) {
        executeFileSubcommand(player, file, distance);
    }

    @Command("musicfile")
    public void executeFileCommand(Player player, @PluginsFolder File file,
            @CommandPermission("plasmo.addon.distnace") @Default("-1") @Flag("distance") SoundDistance distance) {
        executeFileSubcommand(player, file, distance);
    }

    @Command("musicforcefile")
    @CommandPermission("plasmo.addon.file")
    public void executeFileCommand(BukkitCommandActor actor, Player player, @PluginsFolder File file, @Default("-1") @Flag("distance") SoundDistance distance) {
        executeFileSubcommand(player, file, distance);
    }

    private InputStream createProgressStream(InputStream stream, long contentSize, Player player) {
        BossBar bossBar = plugin.getPluginConfig().getBossbarConfiguration().createBossbar();
        if (bossBar == null)
            return stream;
        bossBar.addPlayer(player);
        return new InputStreamProgressWrapper(stream, contentSize).addProgressListener(bossBar::setProgress).addCloseListener(bossBar::removeAll);
    }
}