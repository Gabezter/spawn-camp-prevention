package com.gabezter4.antispawncamping;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeftEvent;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;


public class SpawnCamping extends JavaPlugin implements Listener {
	File warning = null;
	FileConfiguration nw = null;

	File config = null;
	FileConfiguration nc = null;
	
	private int combat = nc.getInt("Combat Time")*1200;
	private int running = nc.getInt("Run Time")*1200;
	
	private boolean inCombat = false;
	private boolean isRunning = false;
	private WorldGuardPlugin getWorldGuard(){
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		if(plugin == null || !(plugin instanceof WorldGuardPlugin)){
			return null;
		}
		return (WorldGuardPlugin) plugin;
	}
	
	WorldGuardPlugin wg = getWorldGuard();
	Location loc;
	RegionManager getRegionManager(World world){
		return getWorldGuard().getRegionManager(world);
	}
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
		ProtectedRegion stringValue1 = getWorldGuard().getRegionManager(getServer().getWorld(sender.getName())).getRegion(args[1]);
		if(cmd.getName().equalsIgnoreCase("scp")){
			if(args[0].equalsIgnoreCase("add")){
				if(sender.hasPermission("scp.admin"))
					if(sender.hasPermission("scp.add"))
						if(stringValue1 != null){
							regions.add(args[1]);
							stringValue1.setFlag(DefaultFlag.PVP, State.ALLOW);
							nw.set("Regions",regions);
							sender.sendMessage(ChatColor.DARK_RED + "Region: " + args[1] + " is now in the system.");
						}return true;
						}
			if(args[0].equalsIgnoreCase("remove")){
				if(sender.hasPermission("scp.admin"))
					if(sender.hasPermission("scp.add"))
						if(regions.contains(args[1])){
							regions.remove(stringValue1);
							nw.set("Regions",regions);
						}
				return true;
			}	
			
		}
		return false;}
	@EventHandler
	public void onRegionEnter(RegionEnterEvent e){
		if(e.getRegion().getId().equals(nw.get(e.getRegion().getId()))){
			if(inCombat == true){
				if(!player.hasPermission("scp.admin"))
					if(!player.hasPermission("scp.bypass"))
						e.setCancelled(true);
						player.sendMessage(ChatColor.DARK_RED + "YOU ARE IN COMBAT!!!!");
		}else{
			inCombat = false;
		}
	}}
	@EventHandler
	public void onRegionLeft(RegionLeftEvent e){
		if(e.getRegion().getId().equals(nw.get(e.getRegion().getId()))){
			if(isRunning == true){
			}
	}}
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