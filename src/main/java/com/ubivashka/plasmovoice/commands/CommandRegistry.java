package com.ubivashka.plasmovoice.commands;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.commands.annotations.PluginsFolder;
import com.ubivashka.plasmovoice.commands.exception.CommandExceptionHandler;
import com.ubivashka.plasmovoice.commands.exception.InvalidFIleException;
import com.ubivashka.plasmovoice.config.PluginConfig;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.bukkit.core.BukkitHandler;

import java.io.File;

public class CommandRegistry {
    private final CommandHandler commandHandler;

    public CommandRegistry(PlasmoVoiceAddon plugin) {
        commandHandler = new BukkitHandler(plugin);
        register(plugin);
        registerCommands();
    }

    private void register(PlasmoVoiceAddon plugin) {
        commandHandler.setExceptionHandler(new CommandExceptionHandler(plugin.getPluginConfig()));

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
    }

    private void registerCommands() {
        commandHandler.register(new MusicCommand());
    }
}

