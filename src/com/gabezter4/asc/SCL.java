package com.gabezter4.asc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SCL implements Listener {
	
	SC plugin;
	
	public SCL(SC instance){
		plugin = instance;
	}

	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.getAction()==Action.LEFT_CLICK_BLOCK && event.getPlayer().getItemInHand().getType() == Material.FIRE){
			try{
				plugin.l1 = event.getClickedBlock().getLocation();
				plugin.m.sendMessage(player, "Postion 1:" + plugin.m.showBlockCoords(plugin.l1));
				event.setCancelled(true);
			}catch(Exception e) {
				plugin.m.sendMessage(player, "Plugin Failed To Create Waypoint!");
				plugin.m.sendConsole("Plugin Failed To Create WayPoint");
			}	
		}
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().getType() == Material.FIRE){
			try{
				plugin.l1 = event.getClickedBlock().getLocation();
				plugin.m.sendMessage(player, "Postion 2:" + plugin.m.showBlockCoords(plugin.l2));
				event.setCancelled(true);
			}catch(Exception e) {
				plugin.m.sendMessage(player, "Plugin Failed To Create Waypoint!");
				plugin.m.sendConsole("Plugin Failed To Create WayPoint");
			}
		}
		if(plugin.protectedBlocks.contains(event.getClickedBlock().getLocation())){
			event.setCancelled(true);
		}
		List<String> cuboidBlocks = new ArrayList<String>();
		Cuboid regions = (Cuboid) plugin.nw.get("Regions");
		
        Location loc1 = new Location(event.getPlayer().getLocation().getWorld(), event.getPlayer().getLocation().getX() + 2, event.getPlayer().getLocation().getY() + 1, event.getPlayer().getLocation().getZ() + 2);
        Location loc2 = new Location(event.getPlayer().getLocation().getWorld(), event.getPlayer().getLocation().getX() - 2, event.getPlayer().getLocation().getY(), event.getPlayer().getLocation().getZ() - 2);
		Cuboid	cuboid = new Cuboid(loc1, loc2);
		for(Block block : cuboid){
			
		
	}
}
		@EventHandler
/*1*/	public void onPlayerMove(PlayerMoveEvent event){
/*2*/		Cuboid getting = (Cuboid) plugin.nw.get("Regions");
/*3*/	for(Cuboid regions : getting){
/*4*/		if(regions.contains(event.getTo()) && !regions.contains(event.getFrom())){
/*5*/			if(plugin.inCombat == true){
/*6*/				event.setCancelled(true);
/*7*/				}
/*8*/			}
/*9*/		}
/*10*/		
/*11*/	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(plugin.protectedBlocks.contains(event.getBlock())){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		if(plugin.protectedBlocks.contains(event.getBlock())){
			event.setCancelled(true);
		}
	}
}
