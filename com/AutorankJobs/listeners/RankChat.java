package com.AutorankJobs.listeners;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.AutorankJobs.configs.PlayerFiles;

public class RankChat implements Listener {
	@EventHandler
	private void playerChat(AsyncPlayerChatEvent e) {
		PlayerFiles cm = PlayerFiles.getConfig(e.getPlayer());
		FileConfiguration config = cm.getConfig();
		e.setFormat(ChatColor.translateAlternateColorCodes('&', config.getString("rank")) + e.getFormat());
	}
}
