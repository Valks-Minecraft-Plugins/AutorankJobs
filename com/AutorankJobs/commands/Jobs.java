package com.AutorankJobs.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.AutorankJobs.configs.PlayerFiles;

import net.md_5.bungee.api.ChatColor;

public class Jobs implements CommandExecutor {

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
				if (args.length < 2) {
					sender.sendMessage(ChatColor.RED + "Specify a job! Jobs are woodcutter, miner, fisher, builder, enchanter and hunter.");
					return true;
				}
				for (String job : jobs) {
					if (job.equals(args[0].toUpperCase())) {
						sender.sendMessage(ChatColor.RED + "You already have that job!");
						return true;
					}
				}
				switch (args[1].toUpperCase()) {
				case "WOODCUTTER":
					addJob(cm, config, sender, jobs, "WOODCUTTER");
					break;
				case "MINER":
					addJob(cm, config, sender, jobs, "MINER");
					break;
				case "FISHER":
					addJob(cm, config, sender, jobs, "FISHER");
					break;
				case "BUILDER":
					addJob(cm, config, sender, jobs, "BUILDER");
					break;
				case "ENCHANTER":
					addJob(cm, config, sender, jobs, "ENCHANTER");
					break;
				case "HUNTER":
					addJob(cm, config, sender, jobs, "HUNTER");
					break;
				default:
					sender.sendMessage(ChatColor.RED + "That job does not exist, jobs are woodcutter, miner, fisher, builder, enchanter and hunter.");
				}
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
