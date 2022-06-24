package com.ubivashka.plasmovoice.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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

@Command("music")
public class MusicCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private PluginConfig config;

    @Subcommand("file")
    public void executeFileSubcommand(Player player, @PluginsFolder File file, @Default("-1") @Flag("distance") SoundDistance distance) {
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
    @CommandPermission("plasmo.addon.file")
    public void executeFileSubcommand(BukkitCommandActor actor, Player player, @PluginsFolder File file,
                                      @Default("-1") @Flag("distance") SoundDistance distance) {
        executeFileSubcommand(player, file, distance);
    }


    @Subcommand("reload")
    @CommandPermission("plasmo.addon.reload")
    public void executeReloadSubcommand(BukkitCommandActor actor) {
        plugin.reload();
        actor.reply(config.getMessages().getMessage("config-reload"));
    }

    @Subcommand("url")
    public void executeUrlSubcommand(Player player, URL musicUrl, @Default("-1") @Flag("distance") SoundDistance distance,
                                     @Switch("forcecache") boolean forceCache) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URLConnection connection = musicUrl.openConnection();
                InputStream connectionStream = createProgressStream(connection.getInputStream(), connection.getContentLength(), player);

                BufferedInputStream bufferedInputStream = new BufferedInputStream(connectionStream);
                Optional<ISoundFormat> soundFormat = plugin.getSoundFormatHolder().findFirstByPredicate(sound -> sound.isSupported(bufferedInputStream));

                if (soundFormat.isPresent() && soundFormat.get().getSettings().isCaching() && !"file".equals(musicUrl.getProtocol()))
                    connectionStream = plugin.getCachedSoundHolder().createCachedInputStream(musicUrl, bufferedInputStream, forceCache);

                playInputStream(player, connectionStream, connection.getContentLengthLong(), distance);
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

    @Command("musicreload")
    public void executeReloadCommand(BukkitCommandActor actor) {
        executeReloadSubcommand(actor);
    }

    private void playInputStream(Player player, InputStream soundStream, long contentSize, SoundDistance distance) throws IOException {
        soundStream = createProgressStream(soundStream, contentSize, player);
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(soundStream)) {
            Optional<ISoundFormat> sound =
                    plugin.getSoundFormatHolder().findFirstByPredicate(soundFormat -> soundFormat.isSupported(bufferedInputStream));
            if (!sound.isPresent()) {
                player.sendMessage(config.getMessages().getMessage("cannot-create-sound"));
                return;
            }
            int soundDistance = distance.getValue() < 0 ? sound.get().getSettings().getDistance() : distance.getValue();
            PlayerAudioSource playerAudioSource = new PlayerAudioSource(player.getUniqueId(), plugin.getPlasmoVoiceSoundPlayer());
            playerAudioSource.sendAudioData(sound.get().newSoundFactory().createSound(bufferedInputStream),
                    IPlasmoVoiceSoundController.of(sound.get(), soundDistance));
        }
    }

    private InputStream createProgressStream(InputStream stream, long contentSize, Player player) {
        BossBar bossBar = plugin.getPluginConfig().getBossbarConfiguration().createBossbar();
        if (bossBar == null)
            return stream;
        bossBar.addPlayer(player);
        return new InputStreamProgressWrapper(stream, contentSize).addProgressListener(bossBar::setProgress).addCloseListener(bossBar::removeAll);
    }
}
