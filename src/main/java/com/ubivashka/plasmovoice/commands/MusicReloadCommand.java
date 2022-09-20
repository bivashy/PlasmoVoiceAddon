package com.ubivashka.plasmovoice.commands;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.commands.exception.SendMessageWithKeyException;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("music")
public class MusicReloadCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;

    @Subcommand("reload")
    @CommandPermission("plasmo.addon.reload")
    public void executeReloadSubcommand() {
        plugin.reload();
        throw new SendMessageWithKeyException("config-reload");
    }

    @Command("musicreload")
    public void executeReloadCommand() {
        executeReloadSubcommand();
    }
}
