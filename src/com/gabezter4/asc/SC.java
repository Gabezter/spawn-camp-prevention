package com.gabezter4.asc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SC extends JavaPlugin {

	SCL sl = new SCL(this);
	SCR sr = new SCR(this);
	SCM sm = new SCM();

	int r = 2;
	int c = 1;
	

	File warning = null;
	FileConfiguration nw = null;
	File config = null;
	FileConfiguration nc = null;

	@Override
	public void onEnable() {

		getServer().getPluginManager().registerEvents(sl, this);
		this.warning = new File(this.getDataFolder(), "regions.yml");
		this.nw = YamlConfiguration.loadConfiguration(warning);

		if (!warning.exists()) {
			this.getLogger().info("Gernerating the hotels.yml file...");
			nw.addDefault("Regions", "");
			nw.options().copyDefaults(true);
			try {
				nw.save(warning);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDisable() {
	}

	public final SCM m = new SCM();

	public ArrayList<Location> protectedBlocks = new ArrayList<Location>();
	public Location l1 = null;
	public Location l2 = null;
	
	Player player;
	
	//Player Booleans
	public Set<String> playerBooleanRun = new HashSet<String>();
	public Set<String> playerBooleanCombat = new HashSet<String>();
	public Set<String> playerBooleanPreRun = new HashSet<String>();
	public boolean run = playerBooleanRun.contains(player.getName());
	public boolean com = playerBooleanCombat.contains(player.getName());
	public boolean preRun = playerBooleanPreRun.contains(player.getName());
	
	int ct = c * 1200;
	int rt = r * 1200;


	List<String> regions = nw.getStringList("Regions");
	

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("scp")) {
			if (args[0].equalsIgnoreCase("add")) {
				if (sender.hasPermission("scp.admin"))
					if (sender.hasPermission("scp.add"))
						sr.protectArea(player, l1, l2);
				regions.add(args[1]);
				nw.set("Regions", regions);
				sender.sendMessage(ChatColor.DARK_RED + "Region: " + args[1]
						+ " is now in the system.");
			}
			return true;
		}
		if (args[0].equalsIgnoreCase("remove")) {
			if (sender.hasPermission("scp.admin"))
				if (sender.hasPermission("scp.remove"))
					if (regions.contains(args[1])) {
						regions.remove(args[1]);
						nw.set("Regions", regions);
					}
			return true;
		}
		return false;
	}

	public void run() {
		if (com) {
			ct--;
			sm.inCombat();
			if (ct == 0) {
				sm.notCombat();
			}
		}
		if (run) {
			rt--;
			sm.isRunning();
			if (rt == 0) {
				sm.notRunning();
			}
		}
	}

}