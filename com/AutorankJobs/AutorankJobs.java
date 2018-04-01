package com.AutorankJobs;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.AutorankJobs.commands.Jobs;
import com.AutorankJobs.configs.LoadPlayerFiles;
import com.AutorankJobs.listeners.Currency;
import com.AutorankJobs.listeners.RankChat;

public class AutorankJobs extends JavaPlugin {
	File rankConfigFile = new File(getDataFolder(), "ranks.yml");
	FileConfiguration rankConfig = YamlConfiguration.loadConfiguration(rankConfigFile);
	
	@Override
	public void onEnable() {
		PluginDescriptionFile p = getDescription();
		sendConsoleMessage(ChatColor.GREEN + p.getName() + " v" + p.getVersion() + " has been enabled.");
		
		registerEvents();
		registerCommands();
		
		if (!rankConfig.isSet("ranks")) {
			List<String> ranks = Arrays.asList("&aExample&2Rank&a1 ", "&3Sexy&bRank&32 ", "&eCats&6Rank&e3 ");
			rankConfig.set("ranks", ranks);
		}
	}
	
	public void sendConsoleMessage(String msg) {
		ConsoleCommandSender console = getServer().getConsoleSender();
		console.sendMessage(msg);
	}
	
	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new LoadPlayerFiles(), this);
		pm.registerEvents(new Currency(), this);
		pm.registerEvents(new RankChat(), this);
		pm.registerEvents(new Jobs(), this);
	}

	private void registerCommands() {
		getCommand("jobs").setExecutor(new Jobs());
	}
	
	public Configuration getRankConfig() {
		return rankConfig;
	}
	
	public void saveRankConfig() {
		try {
			rankConfig.save(rankConfigFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
