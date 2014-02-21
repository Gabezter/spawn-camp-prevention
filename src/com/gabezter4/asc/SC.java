package com.gabezter4.asc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class SC extends JavaPlugin implements Listener {

	public final SCM m = new SCM();
	public final SCL s = new SCL(this);
	
	public ArrayList<Location> protectedBlocks = new ArrayList<Location>();
	public Location l1 = null;
	public Location l2 = null;
	
	File warning = null;
	FileConfiguration nw = null;
	File config = null;
	FileConfiguration nc = null;
	
	private int combat = nc.getInt("Combat Time")*1200;
	private int running = nc.getInt("Run Time")*1200;
	
	boolean inCombat = false;
	boolean isRunning = false;


	@Override
	public void onEnable() {
		this.warning = new File(this.getDataFolder(), "regions.yml");
		this.nw = YamlConfiguration.loadConfiguration(warning);
		this.config = new File(this.getDataFolder(), "config.yml");
		this.nc = YamlConfiguration.loadConfiguration(config);
		if (!config.exists()) {
			this.getLogger().info("Gernerating the config.yml file...");
			nc.addDefault("Run Time", "2");
			nc.addDefault("Combat Time", "1");
			nc.options().copyDefaults(true);
		try {
			nc.save(config);
		} catch (IOException e) {e.printStackTrace();}
	}
		if (!warning.exists()) {
			this.getLogger().info("Gernerating the regions.yml file...");
			nw.addDefault("Regions", "");
			nw.options().copyDefaults(true);
			try {
				nw.save(warning);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}}

	@Override
	public void onDisable() {}
	List<String> regions = nw.getStringList("Regions");
	Player player;
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("scp")){
			if(args[0].equalsIgnoreCase("add")){
				if(sender.hasPermission("scp.admin"))
					if(sender.hasPermission("scp.add"))
						regions.add(args[1]);
						nw.set("Regions",regions);
						sender.sendMessage(ChatColor.DARK_RED + "Region: " + args[1] + " is now in the system.");
						}return true;
						}
			if(args[0].equalsIgnoreCase("remove")){
				if(sender.hasPermission("scp.admin"))
					if(sender.hasPermission("scp.remove"))
						if(regions.contains(args[1])){
							nw.set("Regions",regions);
						}
				return true;
			}
					return false;}

	@EventHandler
	public void EntityDamageEvent(EntityDamageEvent e){
		if(e.getEntity() == player){
			e.getEntityType();
			if(EntityType.PLAYER != null)
				inCombat();
		}
	}
	@EventHandler
	public void PlayerRespawnEvent(PlayerRespawnEvent e){
		if(inCombat == true){
			inCombat=false;
			isRunning=false;
			}
	}	
	public void run(){
		if(inCombat==true){
			combat--;
			inCombat();
			if(combat == 0){
				notCombat();
				}}
		if(isRunning==true){
			running--;
			isRunning();
			if(running == 0){
				notRunning();
				}}
	}
	@EventHandler
	public void PVPPreventing(EntityDamageByEntityEvent e){
		if(isRunning==true){
			if(e.getDamager() instanceof Player){
				if(e.getEntity() instanceof Player){
					e.setCancelled(true);
				}
			}}
	}
	boolean isRunning(){
		player.sendMessage(ChatColor.DARK_BLUE + "You are now in Running Mode!!!");
		player.sendMessage(ChatColor.DARK_BLUE + "PVP is now disabled for you.");
		player.hidePlayer(player);
		return true;
	}
	boolean inCombat(){
		player.sendMessage(ChatColor.DARK_RED + "You are now in Combat!!!");
		run();
		return true;
	}
	boolean notRunning(){
		isRunning=false;
		player.sendMessage(ChatColor.DARK_BLUE + "You are now out of Running Mode!!!");
		player.sendMessage(ChatColor.DARK_BLUE + "PVP is now enabled for you.");
		player.showPlayer(player);
		return true;
	}
	boolean notCombat(){
		inCombat=false;
		player.sendMessage(ChatColor.DARK_GREEN + "You are now out of Combat!!!");
		return true;
	}
}
