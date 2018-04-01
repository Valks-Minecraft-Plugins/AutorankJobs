package com.AutorankJobs.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.AutorankJobs.configs.PlayerFiles;
import com.AutorankJobs.utils.Items;

import net.md_5.bungee.api.ChatColor;

public class Jobs implements CommandExecutor, Listener {
	private Inventory GUI() {
		Inventory inv = Bukkit.createInventory(null, 9, "Jobs");
		inv.setItem(0, Items.invInfo(Material.IRON_PICKAXE, "&fMiner", new String[] {"&7Mine blocks!"}));
		inv.setItem(1, Items.invInfo(Material.IRON_AXE, "&fWood Cutter", new String[] {"&7Cut down trees!"}));
		inv.setItem(2, Items.invInfo(Material.BRICK, "&fBuilder", new String[] {"&7Build blocks!"}));
		inv.setItem(3, Items.invInfo(Material.BOOK_AND_QUILL, "&fEnchanter", new String[] {"&7Enchant some gear!"}));
		inv.setItem(4, Items.invInfo(Material.FISHING_ROD, "&fFisher", new String[] {"&7Go fishing!"}));
		inv.setItem(5, Items.invInfo(Material.IRON_SWORD, "&fHunter", new String[] {"&7Kill stuff!"}));
		return inv;
	}
	
	@EventHandler
	private void registerClicks(InventoryClickEvent e) {
		if (e.getInventory().getName().toLowerCase().equals("jobs")) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			PlayerFiles cm = PlayerFiles.getConfig(p);
			FileConfiguration config = cm.getConfig();
			List<String> jobs = config.getStringList("jobs");
			int slot = e.getSlot();
			switch (slot) {
			case 0:
				addJob(cm, config, p, jobs, "MINER");
				p.closeInventory();
				break;
			case 1:
				addJob(cm, config, p, jobs, "WOODCUTTER");
				p.closeInventory();
				break;
			case 2:
				addJob(cm, config, p, jobs, "BUILDER");
				p.closeInventory();
				break;
			case 3:
				addJob(cm, config, p, jobs, "ENCHANTER");
				p.closeInventory();
				break;
			case 4:
				addJob(cm, config, p, jobs, "FISHER");
				p.closeInventory();
				break;
			case 5:
				addJob(cm, config, p, jobs, "HUNTER");
				p.closeInventory();
				break;
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("jobs")) {
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Usage: /jobs <get|remove|list>");
				return true;
			}
			Player p = Bukkit.getPlayer(sender.getName());
			PlayerFiles cm = PlayerFiles.getConfig(p);
			FileConfiguration config = cm.getConfig();
			
			List<String> jobs = config.getStringList("jobs");
			final int SIZE = jobs.size();
			if (args[0].toLowerCase().equals("remove")) {
				if (SIZE == 0 || config.get("jobs") == null) {
					sender.sendMessage(ChatColor.RED + "You don't have any jobs left to remove!");
					return true;
				}
				config.set("jobs", null);
				sender.sendMessage(ChatColor.GREEN + "Removed all your jobs.");
			}
			if (args[0].toLowerCase().equals("list")) {
				sender.sendMessage(String.join(" ", jobs));
				return true;
			}
			if (args[0].toLowerCase().equals("get")) {
				if (SIZE >= 3) {
					sender.sendMessage(ChatColor.RED + "You can't have more than 3 jobs!");
					return true;
				}
				p.openInventory(GUI());
				return true;
			}
			return true;
		}
		return true;
	}
	
	private void addJob(PlayerFiles cm, FileConfiguration config, CommandSender sender, List<String> jobs, String job) {
		jobs.add(job);
		config.set("jobs", jobs);
		cm.saveConfig();
		sender.sendMessage(ChatColor.GREEN + "You're now a " + job + "!");
	}
}
