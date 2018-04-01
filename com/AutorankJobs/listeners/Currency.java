package com.AutorankJobs.listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;

import com.AutorankJobs.configs.PlayerFiles;
import com.AutorankJobs.rank.CheckRank;

public class Currency implements Listener {
	@EventHandler
	private void onBlockBreak(BlockBreakEvent e) {
		final Material BLOCK_TYPE = e.getBlock().getType();
		Player p = e.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		List<String> jobs = config.getStringList("jobs");
		for (String job : jobs) {
			if (job.equals("MINER")) {
				switch (BLOCK_TYPE) {
				case STONE:
					config.set("xp", config.getInt("xp") + 1);
					CheckRank.checkRankChange(p);
					cm.saveConfig();
					break;
				case COAL_ORE:
				case DIAMOND_ORE:
				case EMERALD_ORE:
				case GLOWING_REDSTONE_ORE:
				case GOLD_ORE:
				case IRON_ORE:
				case LAPIS_ORE:
				case QUARTZ_ORE:
				case REDSTONE_ORE:
					config.set("xp", config.getInt("xp") + 10);
					CheckRank.checkRankChange(p);
					cm.saveConfig();
					break;
				default:
					break;
				}
			}
		}
		
		for (String job : jobs) {
			if (job.equals("WOODCUTTER")) {
				switch (BLOCK_TYPE) {
				case LOG:
				case LOG_2:
					config.set("xp", config.getInt("xp") + 4);
					CheckRank.checkRankChange(p);
					cm.saveConfig();
					break;
				default:
					break;
				}
			}
		}
		
	}

	@EventHandler
	private void onEnchant(EnchantItemEvent e) {
		Player p = e.getEnchanter();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		
		List<String> jobs = config.getStringList("jobs");
		if (jobs.contains("ENCHANTER")) {
			config.set("xp", config.getInt("xp") + 30);
			CheckRank.checkRankChange(p);
			cm.saveConfig();
		}
	}

	@EventHandler
	private void onMobDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Monster) {
			if (e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				PlayerFiles cm = PlayerFiles.getConfig(p);
				FileConfiguration config = cm.getConfig();
				List<String> jobs = config.getStringList("jobs");
				for (String job : jobs) {
					if (job.equals("HUNTER")) {
						config.set("xp", config.getInt("xp") + 15);
						CheckRank.checkRankChange(p);
						cm.saveConfig();
					}
				}
				
			}
		}
	}

	@EventHandler
	private void onFish(PlayerFishEvent e) {
		Player p = e.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		List<String> jobs = config.getStringList("jobs");
		for (String job : jobs) {
			if (job.equals("FISHER")) {
				config.set("xp", config.getInt("xp") + 20);
				CheckRank.checkRankChange(p);
				cm.saveConfig();
			}
		}
		
	}

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		List<String> jobs = config.getStringList("jobs");
		for (String job : jobs) {
			if (job.equals("BUILDER")) {
				config.set("xp", config.getInt("xp") + 1);
				CheckRank.checkRankChange(p);
				cm.saveConfig();
			}
		}
	}
}
