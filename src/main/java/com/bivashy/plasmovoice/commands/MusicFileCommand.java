package com.bivashy.plasmovoice.commands;

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

import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.bivashy.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.bivashy.plasmovoice.commands.annotations.PluginsFolder;
import com.bivashy.plasmovoice.commands.argument.SoundDistance;
import com.bivashy.plasmovoice.commands.exception.SendMessageWithKeyException;
import com.bivashy.plasmovoice.config.settings.command.FileCommandSettings;
import com.bivashy.plasmovoice.progress.InputStreamProgressWrapper;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Flag;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("music")
public class MusicFileCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private FileCommandSettings settings;

    @Subcommand("file")
    public void executeFileSubcommand(Player player, PlasmoVoiceSoundPlayer soundPlayer, @PluginsFolder File file,
                                      @Default("-1") @Flag("distance") SoundDistance distance) {
        if (!settings.isEnabled())
            throw new SendMessageWithKeyException("file-command-disabled");
        if (settings.getPermission().isPresent() && !player.hasPermission(settings.getPermission().get()))
            throw new SendMessageWithKeyException("not-enough-permission");
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Path path = file.toPath();
            try {
                long fileSize = Files.size(path);
                if (settings.getSizeLimit() > 0 && fileSize > settings.getSizeLimit()) {
                    player.sendMessage(plugin.getPluginConfig().getMessages().getMessage("invalid-size"));
                    return;
                }

                InputStream fileStream = new BufferedInputStream(createProgressStream(Files.newInputStream(path), Files.size(path), player));
                Optional<PlasmoVoiceSoundPlaySession> soundPlaySession = soundPlayer.playSound(file, fileStream);
                if (!soundPlaySession.isPresent()) {
                    player.sendMessage(plugin.getPluginConfig().getMessages().getMessage("cannot-create-sound"));
                    return;
                }
                soundPlaySession.ifPresent(session -> session.getSoundController()
                        .setDistance(distance.getValue(session.getSound().getSoundFormat().getSettings().getDistance())));
                fileStream.close();
            } catch(IOException e) {
                player.sendMessage(plugin.getPluginConfig().getMessages().getMessage("error-occurred"));
                e.printStackTrace();
            }
        });
    }

    @Subcommand("force file")
    @CommandPermission("plasmo.addon.file")
    public void executeFileSubcommand(BukkitCommandActor actor, Player player, PlasmoVoiceSoundPlayer soundPlayer, @PluginsFolder File file,
            @Default("-1") @Flag("distance") SoundDistance distance) {
        executeFileSubcommand(player, soundPlayer, file, distance);
    }

    @Command("musicfile")
    public void executeFileCommand(Player player, PlasmoVoiceSoundPlayer soundPlayer, @PluginsFolder File file,
            @CommandPermission("plasmo.addon.distnace") @Default("-1") @Flag("distance") SoundDistance distance) {
        executeFileSubcommand(player, soundPlayer, file, distance);
    }

    @Command("musicforcefile")
    @CommandPermission("plasmo.addon.file")
    public void executeFileCommand(BukkitCommandActor actor, Player player, PlasmoVoiceSoundPlayer soundPlayer, @PluginsFolder File file,
            @Default("-1") @Flag("distance") SoundDistance distance) {
        executeFileSubcommand(player, soundPlayer, file, distance);
    }

    private InputStream createProgressStream(InputStream stream, long contentSize, Player player) {
        BossBar bossBar = plugin.getPluginConfig().getBossbarConfiguration().createBossbar();
        if (bossBar == null || contentSize == -1)
            return stream;
        bossBar.addPlayer(player);
        return new InputStreamProgressWrapper(stream, contentSize).addProgressListener(bossBar::setProgress).addCloseListener(bossBar::removeAll);
    }
}