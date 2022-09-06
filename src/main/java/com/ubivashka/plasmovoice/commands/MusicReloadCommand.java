package com.ubivashka.plasmovoice.commands;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.config.PluginConfig;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("reload")
public class MusicReloadCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;
    @Dependency
    private PluginConfig config;

    @Subcommand("reload")
    @CommandPermission("plasmo.addon.reload")
    public void executeReloadSubcommand(BukkitCommandActor actor) {
        plugin.reload();
        actor.reply(config.getMessages().getMessage("config-reload"));
    }

    @Command("musicreload")
    public void executeReloadCommand(BukkitCommandActor actor) {
        executeReloadSubcommand(actor);
    }
}
