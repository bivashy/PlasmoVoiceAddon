package com.ubivashka.plasmovoice.commands;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.commands.annotations.PluginsFolder;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.SoundBuilder;
import org.bukkit.Bukkit;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.BukkitCommandActor;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Command("music")
public class MusicFileCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private PluginConfig config;

    @Subcommand("file")
    public void executeSubcommand(BukkitCommandActor actor, @PluginsFolder File file, @Default("100") @Flag("distance") int distance) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                ISound sound = new SoundBuilder(new BufferedInputStream(Files.newInputStream(file.toPath())))
                        .build();

                if (sound == null) {
                    actor.reply(config.getMessages().getMessage("cannot-create-sound"));
                    return;
                }

                PlayerAudioSource playerAudioSource = new PlayerAudioSource(actor.getUniqueId(),
                        plugin.getPlasmoVoiceSoundPlayer());
                playerAudioSource.sendAudioData(sound, distance);
            } catch(UnsupportedAudioFileException |
                    IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Command("musicfile")
    public void executeCommand(BukkitCommandActor actor, @PluginsFolder File file, @Default("100") @Flag("distance") int distance) {
        executeSubcommand(actor, file, distance);
    }
}
