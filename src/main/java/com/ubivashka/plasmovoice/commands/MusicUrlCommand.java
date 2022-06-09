package com.ubivashka.plasmovoice.commands;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.config.PluginConfig;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.SoundBuilder;
import org.bukkit.Bukkit;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.BukkitCommandActor;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Command("music")
public class MusicUrlCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private PluginConfig config;

    @Subcommand("url")
    public void executeSubcommand(BukkitCommandActor actor, URL musicUrl, @Default("100") @Flag("distance") int distance) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                InputStream inputStream = musicUrl.openStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                ISound sound = new SoundBuilder(bufferedInputStream).build();

                if (sound == null) {
                    actor.reply(config.getMessages().getMessage("cannot-create-sound"));
                    return;
                }

                PlayerAudioSource playerAudioSource = new PlayerAudioSource(actor.getUniqueId(), plugin.getPlasmoVoiceSoundPlayer());
                playerAudioSource.sendAudioData(sound, distance);
            } catch(UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Command("musicurl")
    public void executeCommand(BukkitCommandActor actor, URL musicUrl, @Default("100") @Flag("distance") int distance) {
        executeSubcommand(actor, musicUrl, distance);
    }
}
