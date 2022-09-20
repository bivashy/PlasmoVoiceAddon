package com.ubivashka.plasmovoice.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.ubivashka.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.ubivashka.plasmovoice.commands.argument.SoundDistance;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.config.settings.command.UrlCommandSettings;
import com.ubivashka.plasmovoice.progress.InputStreamProgressWrapper;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Flag;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.exception.SendMessageException;

@Command("music")
public class MusicURLCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private UrlCommandSettings settings;

    @Subcommand("url")
    public void executeUrlSubcommand(Player player, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance) {
        if (!settings.isEnabled())
            throw new SendMessageException(config.getMessages().getMessage("url-command-disabled"));
        if (settings.getPermission().isPresent() && !player.hasPermission(settings.getPermission().get()))
            throw new SendMessageException(config.getMessages().getMessage("not-enough-permission"));
        if (!settings.getWhitelistSchemes().contains(musicUrl.getProtocol()))
            throw new SendMessageException(config.getMessages().getMessage("invalid-scheme"));
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URLConnection connection = musicUrl.openConnection();
                if (settings.shouldBlockUndefinedSize() && connection.getContentLengthLong() == -1) {
                    player.sendMessage(plugin.getPluginConfig().getMessages().getMessage("invalid-size"));
                    return;
                }
                if (settings.getSizeLimit() > 0 && connection.getContentLengthLong() > settings.getSizeLimit()) {
                    player.sendMessage(plugin.getPluginConfig().getMessages().getMessage("invalid-size"));
                    return;
                }

                PlasmoVoiceSoundPlayer soundPlayer = plugin.getPlasmoVoiceSoundPlayer(player.getUniqueId());
                InputStream urlStream = new BufferedInputStream(createProgressStream(connection.getInputStream(), connection.getContentLength(), player));
                Optional<PlasmoVoiceSoundPlaySession> soundPlaySession = soundPlayer.playSound(musicUrl, urlStream);
                if (!soundPlaySession.isPresent()) {
                    player.sendMessage(plugin.getPluginConfig().getMessages().getMessage("cannot-create-sound"));
                    return;
                }

                soundPlaySession.ifPresent(session -> {
                    session.getSoundController()
                            .setDistance(distance.getValue(session.getSound().getSoundFormat().getSettings().getDistance()));
                    session.playSound();
                });
                urlStream.close();
            } catch(IOException e) {
                player.sendMessage(plugin.getPluginConfig().getMessages().getMessage("error-occurred"));
                e.printStackTrace();
            }
        });
    }

    @Subcommand("force url")
    @CommandPermission("plasmo.addon.url")
    public void executeUrlSubcommand(BukkitCommandActor actor, Player player, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance) {
        executeUrlSubcommand(player, musicUrl, distance);
    }

    @Command("musicurl")
    public void executeUrlCommand(Player player, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance) {
        executeUrlSubcommand(player, musicUrl, distance);
    }

    @Command("musicforceurl")
    @CommandPermission("plasmo.addon.url")
    public void executeUrlCommand(BukkitCommandActor actor, Player player, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance) {
        executeUrlSubcommand(player, musicUrl, distance);
    }

    private InputStream createProgressStream(InputStream stream, long contentSize, Player player) {
        BossBar bossBar = plugin.getPluginConfig().getBossbarConfiguration().createBossbar();
        if (bossBar == null || contentSize == -1)
            return stream;
        bossBar.addPlayer(player);
        return new InputStreamProgressWrapper(stream, contentSize).addProgressListener(bossBar::setProgress).addCloseListener(bossBar::removeAll);
    }
}