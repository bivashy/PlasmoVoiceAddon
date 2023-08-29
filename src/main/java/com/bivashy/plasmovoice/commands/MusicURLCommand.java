package com.bivashy.plasmovoice.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.bivashy.plasmovoice.audio.player.session.PlasmoVoiceSoundPlaySession;
import com.bivashy.plasmovoice.commands.argument.SoundDistance;
import com.bivashy.plasmovoice.commands.exception.SendMessageWithKeyException;
import com.bivashy.plasmovoice.config.settings.command.UrlCommandSettings;
import com.bivashy.plasmovoice.progress.InputStreamProgressWrapper;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Flag;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("music")
public class MusicURLCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private UrlCommandSettings settings;

    @Subcommand("url")
    public void executeUrlSubcommand(Player player, PlasmoVoiceSoundPlayer soundPlayer, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance) {
        if (!settings.isEnabled())
        throw new SendMessageWithKeyException("url-command-disabled");
        if (settings.getPermission().isPresent() && !player.hasPermission(settings.getPermission().get()))
            throw new SendMessageWithKeyException("not-enough-permission");
        if (!settings.getWhitelistSchemes().contains(musicUrl.getProtocol()))
            throw new SendMessageWithKeyException("invalid-scheme");
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

                InputStream urlStream = new BufferedInputStream(createProgressStream(connection.getInputStream(), connection.getContentLength(), player));
                Optional<PlasmoVoiceSoundPlaySession> soundPlaySession = soundPlayer.playSound(musicUrl, urlStream);
                if (!soundPlaySession.isPresent()) {
                    player.sendMessage(plugin.getPluginConfig().getMessages().getMessage("cannot-create-sound"));
                    return;
                }

                soundPlaySession.ifPresent(session -> session.getSoundController()
                        .setDistance(distance.getValue(session.getSound().getSoundFormat().getSettings().getDistance())));
                urlStream.close();
            } catch(IOException e) {
                player.sendMessage(plugin.getPluginConfig().getMessages().getMessage("error-occurred"));
                e.printStackTrace();
            }
        });
    }

    @Subcommand("force url")
    @CommandPermission("plasmo.addon.url")
    public void executeUrlSubcommand(BukkitCommandActor actor, Player player, PlasmoVoiceSoundPlayer soundPlayer, URL musicUrl,
            @Default("-1") @Flag("distance") SoundDistance distance) {
        executeUrlSubcommand(player, soundPlayer, musicUrl, distance);
    }

    @Command("musicurl")
    public void executeUrlCommand(Player player, PlasmoVoiceSoundPlayer soundPlayer, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance) {
        executeUrlSubcommand(player, soundPlayer, musicUrl, distance);
    }

    @Command("musicforceurl")
    @CommandPermission("plasmo.addon.url")
    public void executeUrlCommand(BukkitCommandActor actor, Player player, PlasmoVoiceSoundPlayer soundPlayer, URL musicUrl,
            @Default("-1") @Flag("distance") SoundDistance distance) {
        executeUrlSubcommand(player, soundPlayer, musicUrl, distance);
    }

    private InputStream createProgressStream(InputStream stream, long contentSize, Player player) {
        BossBar bossBar = plugin.getPluginConfig().getBossbarConfiguration().createBossbar();
        if (bossBar == null || contentSize == -1)
            return stream;
        bossBar.addPlayer(player);
        return new InputStreamProgressWrapper(stream, contentSize).addProgressListener(bossBar::setProgress).addCloseListener(bossBar::removeAll);
    }
}