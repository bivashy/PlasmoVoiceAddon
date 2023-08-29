package com.bivashy.plasmovoice.commands;

import org.bukkit.entity.Player;

import com.bivashy.plasmovoice.PlasmoVoiceAddon;
import com.bivashy.plasmovoice.audio.player.PlasmoVoiceSoundPlayer;
import com.bivashy.plasmovoice.audio.player.session.ISoundPlaySession;

import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.command.CommandActor;

@Command("music")
public class MusicControlCommand {
    @Dependency
    private PlasmoVoiceAddon plugin;

    @Subcommand("pause")
    public void pauseMusic(Player player, PlasmoVoiceSoundPlayer soundPlayer) {
        soundPlayer.getSource().getCurrentSession().ifPresent(ISoundPlaySession::pause);
    }

    @Subcommand("resume")
    public void resumeMusic(Player player, PlasmoVoiceSoundPlayer soundPlayer) {
        soundPlayer.getSource().getCurrentSession().ifPresent(ISoundPlaySession::continuePlaying);
    }

    @Subcommand("next")
    public void nextMusic(Player player, PlasmoVoiceSoundPlayer soundPlayer) {
        soundPlayer.forceNextSound();
    }

    @Subcommand("force pause")
    @CommandPermission("plasmo.addon.pause")
    public void pauseMusic(CommandActor actor, Player player) {
        pauseMusic(player, plugin.getPlasmoVoiceSoundPlayer(player.getUniqueId()));
    }

    @Subcommand("force resume")
    @CommandPermission("plasmo.addon.resume")
    public void resumeMusic(CommandActor actor, Player player) {
        resumeMusic(player, plugin.getPlasmoVoiceSoundPlayer(player.getUniqueId()));
    }

    @Subcommand("force next")
    @CommandPermission("plasmo.addon.next")
    public void nextMusic(CommandActor actor, Player player) {
        nextMusic(player, plugin.getPlasmoVoiceSoundPlayer(player.getUniqueId()));
    }
}
