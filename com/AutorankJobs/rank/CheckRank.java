package com.AutorankJobs.rank;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.AutorankJobs.AutorankJobs;
import com.AutorankJobs.configs.PlayerFiles;

import net.md_5.bungee.api.ChatColor;

public class CheckRank {
	public static void checkRankChange(Player p) {
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		int xp = config.getInt("xp");
		
		List<String> ranks = AutorankJobs.getPlugin(AutorankJobs.class).getRankConfig().getStringList("ranks");
		int RANKS = ranks.size();
		
		int rankLevel = (int) Math.pow(xp, 1.0 / 4.0);
		
		if (rankLevel <= RANKS) {
			if (!config.getString("rank").equals(ranks.get(rankLevel))){
				config.set("rank", ranks.get(rankLevel));
			} else {
				p.sendMessage(ChatColor.DARK_GREEN + "You ranked up to " + ranks.get(rankLevel));
			}
		}
	}
}
