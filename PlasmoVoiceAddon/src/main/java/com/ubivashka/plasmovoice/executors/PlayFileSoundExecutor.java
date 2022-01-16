package com.ubivashka.plasmovoice.executors;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.SoundFormat;
import com.ubivashka.plasmovoice.sound.mp3.MP3Sound;
import com.ubivashka.plasmovoice.sound.pcm.AudioStreamSound;

public class PlayFileSoundExecutor implements CommandExecutor {
	private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can play sound!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length >= 2) {
			File file = new File(PLUGIN.getDataFolder() + File.separator + args[0]);
			SoundFormat soundFormat = SoundFormat.findMatchingSoundFormat(args[1]);
			Bukkit.getScheduler().runTaskAsynchronously(PLUGIN, () -> {
				try {
					ISound sound = null;

					switch (soundFormat) {
					case WAV:
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
						sound = new AudioStreamSound(audioInputStream,
								PLUGIN.getPlasmoVoiceSoundPlayer().getCodecHolder(), true);
						break;
					case MP3:
						sound = new MP3Sound(new BufferedInputStream(Files.newInputStream(file.toPath())),
								PLUGIN.getPlasmoVoiceSoundPlayer());
						break;
					}

					if (sound == null) {
						player.sendMessage("Проблемы при создании музыки");
						return;
					}
					PlayerAudioSource playerAudioSource = new PlayerAudioSource(player.getUniqueId(),
							PLUGIN.getPlasmoVoiceSoundPlayer());
					playerAudioSource.sendAudioData(sound, 100);

				} catch (UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
				}
			});
		}
		return true;
	}
}
