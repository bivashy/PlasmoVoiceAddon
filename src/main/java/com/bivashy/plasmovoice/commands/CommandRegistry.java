package com.bivashy.plasmovoice.commands;

import java.io.File;

import org.bukkit.entity.Player;

import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.bivashy.plasmovoice.commands.annotations.PluginsFolder;
import com.bivashy.plasmovoice.commands.argument.SoundDistance;
import com.bivashy.plasmovoice.commands.exception.CommandExceptionHandler;
import com.bivashy.plasmovoice.commands.exception.InvalidFIleException;
import com.bivashy.plasmovoice.commands.exception.SendMessageWithKeyException;
import com.bivashy.plasmovoice.config.PluginConfig;
import com.bivashy.plasmovoice.config.settings.command.FileCommandSettings;
import com.bivashy.plasmovoice.config.settings.command.UrlCommandSettings;

import revxrsal.commands.CommandHandler;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.core.BukkitHandler;
import revxrsal.commands.exception.NoPermissionException;

public class CommandRegistry {
    private final CommandHandler commandHandler;

    public CommandRegistry(PlasmoVoiceAddon plugin) {
        commandHandler = new BukkitHandler(plugin);
        register(plugin);
        registerCommands();
    }

    private void register(PlasmoVoiceAddon plugin) {
        commandHandler.setExceptionHandler(new CommandExceptionHandler(plugin.getPluginConfig()));
        commandHandler.registerExceptionHandler(SendMessageWithKeyException.class, (actor, e) -> e.sendTo(actor));
        commandHandler.registerContextResolver(PlasmoVoiceSoundPlayer.class, context -> plugin.getPlasmoVoiceSoundPlayer(context.actor().getUniqueId()));

        commandHandler.registerValueResolver(SoundDistance.class, context -> {
            int distance = context.popInt();
            Player player = context.actor().as(BukkitCommandActor.class).getAsPlayer();
            if (!Integer.toString(distance).equals(context.parameter().getDefaultValue()) && player != null)
                if (!player.hasPermission("plasmo.addon.distance." + distance))
                    throw new NoPermissionException(context.command(), context.actor());
            return new SoundDistance(distance);
        });

        commandHandler.registerValueResolver(File.class, (context) -> {
            String argument = context.pop();
            if (context.parameter().hasAnnotation(PluginsFolder.class)) {
                File dataFolder = plugin.getDataFolder();
                File possibleFile = new File(dataFolder + File.separator + argument);
                if (possibleFile.exists())
                    return possibleFile;
                if (context.parameter().getAnnotation(PluginsFolder.class).onlyPluginsFolder())
                    throw new InvalidFIleException(context.parameter(), argument);
            }
            File possibleFile = new File(argument);
            if (!context.parameter().isOptional() && !possibleFile.exists())
                throw new InvalidFIleException(context.parameter(), argument);
            return possibleFile;
        });

        commandHandler.registerDependency(PluginConfig.class, plugin.getPluginConfig());
        commandHandler.registerDependency(UrlCommandSettings.class, plugin.getPluginConfig().getMusicCommandSettings().getUrlCommandSettings());
        commandHandler.registerDependency(FileCommandSettings.class, plugin.getPluginConfig().getMusicCommandSettings().getFileCommandSettings());
    }

    private void registerCommands() {
        commandHandler.register(new MusicReloadCommand(), new MusicFileCommand(), new MusicURLCommand(), new MusicControlCommand());
    }
}

