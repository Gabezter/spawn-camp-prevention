package com.gabezter4.asc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class SC extends JavaPlugin {

	SCL sl = new SCL(this);
	SCR sr = new SCR(this);
	SCM sm = new SCM();

	int r = 2;
	int c = 1;

	@Override
	public void onEnable() {
		this.getConfig().addDefault("Regions", null);
		this.saveDefaultConfig();

		getServer().getPluginManager().registerEvents(sl, this);
	}

	@Override
	public void onDisable() {
	}

	public final SCM m = new SCM();

	public ArrayList<Location> protectedBlocks = new ArrayList<Location>();
	public Location l1 = null;
	public Location l2 = null;


	// Player Booleans
	public Set<String> playerBooleanRun = new HashSet<String>();
	public Set<String> playerBooleanCombat = new HashSet<String>();
	public Set<String> playerBooleanPreRun = new HashSet<String>();

	int ct = c * 1200;
	int rt = r * 1200;

	List<String> regions = new ArrayList<String>();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("scp")) {
			if (args[0].equalsIgnoreCase("add")) {
				if (sender.hasPermission("scp.add")) {
					if (l1 != null) {
						if (l2 != null) {
							sr.protectArea((Player) sender, l1, l2);
							regions.add(args[1]);
							this.getConfig().set("Regions", regions);
							this.saveConfig();
							sender.sendMessage(ChatColor.DARK_BLUE + "Region: "
									+ args[1] + " is now in the system.");
						} else {
							sender.sendMessage(ChatColor.DARK_RED
									+ "You need to select the 2nd point.");
							sender.sendMessage(ChatColor.DARK_RED
									+ "To select the 2nd point right click with the fire block.");
						}
					} else {
						sender.sendMessage(ChatColor.DARK_RED
								+ "You need to select the 1st point.");
						sender.sendMessage(ChatColor.DARK_RED
								+ "To select the 1st point left click with the fire block.");
					}
				} else if (sender.hasPermission("scp.admin")) {
					if (l1 != null) {
						if (l2 != null) {
							sr.protectArea((Player)sender, l1, l2);
							regions.add(args[1]);
							this.getConfig().set("Regions", regions);
							this.saveConfig();
							sender.sendMessage(ChatColor.DARK_BLUE + "Region: "
									+ args[1] + " is now in the system.");
						} else {
							sender.sendMessage(ChatColor.DARK_RED
									+ "You need to select the 2nd point.");
							sender.sendMessage(ChatColor.DARK_RED
									+ "To select the 2nd point right click with the fire block.");
						}
					} else {
						sender.sendMessage(ChatColor.DARK_RED
								+ "You need to select the 1st point.");
						sender.sendMessage(ChatColor.DARK_RED
								+ "To select the 1st point left click with the fire block.");
					}
				}
			}
			return true;
		}
		if (args[0].equalsIgnoreCase("remove")) {
			if (sender.hasPermission("scp.remove")) {
				if (regions.contains(args[1])) {
					regions.remove(args[1]);
					this.getConfig().set("Regions", regions);
					this.saveConfig();
				}
			} else if (sender.hasPermission("scp.admin")) {
				if (regions.contains(args[1])) {
					regions.remove(args[1]);
					this.getConfig().set("Regions", regions);
					this.saveConfig();
				}
			}
			return true;
		}
		if (args[0].equalsIgnoreCase("tool")) {
			PlayerInventory inv = ((Player) sender).getInventory();
			ItemStack fire = new ItemStack(Material.FIRE);
			if (sender.hasPermission("scp.tool")) {
				inv.addItem(fire);
				return true;
			} else if (sender.hasPermission("scp.admin"))
				inv.addItem(fire);
			return true;
		}
		
		if(args[0].equalsIgnoreCase(null)){
			sender.sendMessage(ChatColor.WHITE + "Spawn Camp Prevention Help Page.");
			sender.sendMessage(ChatColor.WHITE + "/scp tool   - Gives the tool to slect the region.");
			sender.sendMessage(ChatColor.WHITE + "/scp add    - Adds the region.");
			sender.sendMessage(ChatColor.WHITE + "/scp remove - Removes the region.");
			sender.sendMessage(ChatColor.WHITE + "/scp list   - Lists the regions.");
			sender.sendMessage(ChatColor.WHITE + "/scp        - Gives you this page!!!");
			return true;
		}
		if(args[0].equalsIgnoreCase("list")){
			sender.sendMessage(ChatColor.WHITE + this.getConfig().getString("Regions"));
			return true;
		}
		
		return false;
	}

	Player player;
	public void run() {
		if (playerBooleanCombat.contains(player.getName())) {
			ct--;
			sm.inCombat();
			if (ct == 0) {
				sm.notCombat();
			}
		}
		if (playerBooleanRun.contains(player.getName())) {
			rt--;
			sm.isRunning();
			if (rt == 0) {
				sm.notRunning();
			}
		}
	}

}