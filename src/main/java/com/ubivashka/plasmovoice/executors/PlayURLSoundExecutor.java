package com.ubivashka.plasmovoice.executors;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ubivashka.plasmovoice.PlasmoVoiceAddon;
import com.ubivashka.plasmovoice.audio.sources.PlayerAudioSource;
import com.ubivashka.plasmovoice.sound.ISound;
import com.ubivashka.plasmovoice.sound.SoundBuilder;

public class PlayURLSoundExecutor implements CommandExecutor {
	private static final PlasmoVoiceAddon PLUGIN = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can play sound!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length >= 1) {
			try {
				URL url = new URL(args[0]);
				Bukkit.getScheduler().runTaskAsynchronously(PLUGIN, () -> {
					try {
						InputStream inputStream = url.openStream();
						BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
						ISound sound = new SoundBuilder(bufferedInputStream).build();

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
			} catch (MalformedURLException e) {
				sender.sendMessage("Неправильный URL!");
				return true;
			}
		}
		return true;
	}

}
