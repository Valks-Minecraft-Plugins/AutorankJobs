package com.AutorankJobs.configs;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoadPlayerFiles implements Listener {
	@EventHandler
	public void loadFiles(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p.getUniqueId());
		if (!p.hasPlayedBefore()) {
			FileConfiguration f = cm.getConfig();
			f.set("name", p.getName());
			f.set("uuid", p.getUniqueId().toString());
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
			f.set("join_date", format.format(now));
			f.set("last_join", format.format(now));
			f.set("rank", "");
			List<String> jobs = Arrays.asList("MINER");
			f.set("jobs", jobs);
			f.set("xp", 0);
			cm.saveConfig();
		} else {
			FileConfiguration f = cm.getConfig();
			f.set("name", p.getName());
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
			f.set("last_join", format.format(now));
			if (!f.isSet("rank")) {
				f.set("rank", "");
			}
			if (!f.isSet("jobs")) {
				List<String> jobs = Arrays.asList("MINER");
				f.set("jobs", jobs);
			}
			if (!f.isSet("xp")) {
				f.set("xp", 0);
			}
			cm.saveConfig();
		}
	}
}