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
import com.ubivashka.plasmovoice.audio.player.controller.IPlasmoVoiceSoundController;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.commands.argument.SoundDistance;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.config.settings.command.UrlCommandSettings;
import com.ubivashka.plasmovoice.progress.InputStreamProgressWrapper;
import com.ubivashka.plasmovoice.sound.ISoundFormat;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Flag;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.annotation.Switch;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.exception.SendMessageException;

@Command("music")
public class MusicURLCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private PluginConfig config;
    @Dependency
    private UrlCommandSettings settings;

    @Subcommand("url")
    public void executeUrlSubcommand(Player player, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance,
            @Switch("forcecache") boolean forceCache) {
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
                    player.sendMessage(config.getMessages().getMessage("invalid-size"));
                    return;
                }
                if (settings.getSizeLimit() > 0 && connection.getContentLengthLong() > settings.getSizeLimit()) {
                    player.sendMessage(config.getMessages().getMessage("invalid-size"));
                    return;
                }

                InputStream urlStream = new BufferedInputStream(createProgressStream(connection.getInputStream(), connection.getContentLength(), player));
                Optional<ISoundFormat> optionalSoundFormat = getSoundFormat(musicUrl, urlStream);

                if (!optionalSoundFormat.isPresent()) {
                    player.sendMessage(config.getMessages().getMessage("cannot-create-sound"));
                    urlStream.close();
                    return;
                }
                ISoundFormat soundFormat = optionalSoundFormat.get();

                if (settings.getCachingSettings().isEnabled())
                    urlStream = plugin.getCachedSoundHolder().createCachedInputStream(musicUrl, urlStream, forceCache);

                PlayerAudioSource playerAudioSource = new PlayerAudioSource(player.getUniqueId(), plugin.getPlasmoVoiceSoundPlayer());
                playerAudioSource.sendAudioData(soundFormat.newSoundFactory().createSound(musicUrl, urlStream),
                        IPlasmoVoiceSoundController.of(soundFormat, distance.getValue(soundFormat.getSettings().getDistance())));
                urlStream.close();
            } catch(IOException e) {
                player.sendMessage(config.getMessages().getMessage("error-occurred"));
                e.printStackTrace();
            }
        });
    }

    @Subcommand("force url")
    @CommandPermission("plasmo.addon.url")
    public void executeUrlSubcommand(BukkitCommandActor actor, Player player, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance,
            @Switch("forcecache") boolean forceCache) {
        executeUrlSubcommand(player, musicUrl, distance, forceCache);
    }

    @Command("musicurl")
    public void executeUrlCommand(Player player, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance,
            @Switch("forcecache") boolean forceCache) {
        executeUrlSubcommand(player, musicUrl, distance, forceCache);
    }

    @Command("musicforceurl")
    @CommandPermission("plasmo.addon.url")
    public void executeUrlCommand(BukkitCommandActor actor, Player player, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance,
            @Switch("forcecache") boolean forceCache) {
        executeUrlSubcommand(player, musicUrl, distance, forceCache);
    }

    private InputStream createProgressStream(InputStream stream, long contentSize, Player player) {
        BossBar bossBar = plugin.getPluginConfig().getBossbarConfiguration().createBossbar();
        if (bossBar == null || contentSize == -1)
            return stream;
        bossBar.addPlayer(player);
        return new InputStreamProgressWrapper(stream, contentSize).addProgressListener(bossBar::setProgress).addCloseListener(bossBar::removeAll);
    }

    private Optional<ISoundFormat> getSoundFormat(URL url, InputStream inputStream) {
        return plugin.getSoundFormatHolder().findFirstByPredicate(format -> format.isSupported(url, inputStream));
    }
}