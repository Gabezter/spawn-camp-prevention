package com.gabezter4.asc;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SCL implements Listener {

	Player player;
	Player player1;
	SC plugin;

	public SCL(SC instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				&& event.getPlayer().getItemInHand().getType() == Material.FIRE) {
			try {
				plugin.l1 = event.getClickedBlock().getLocation();
				plugin.m.sendMessage(player,
						"Postion 1:" + plugin.m.showBlockCoords(plugin.l1));
				event.setCancelled(true);
			} catch (Exception e) {
				plugin.m.sendMessage(player,
						"Plugin Failed To Create Waypoint!");
				plugin.m.sendConsole("Plugin Failed To Create WayPoint");
			}
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& player.getItemInHand().getType() == Material.FIRE) {
			try {
				plugin.l1 = event.getClickedBlock().getLocation();
				plugin.m.sendMessage(player,
						"Postion 2:" + plugin.m.showBlockCoords(plugin.l2));
				event.setCancelled(true);
			} catch (Exception e) {
				plugin.m.sendMessage(player,
						"Plugin Failed To Create Waypoint!");
				plugin.m.sendConsole("Plugin Failed To Create WayPoint");
			}
		}
		if (plugin.protectedBlocks.contains(event.getClickedBlock()
				.getLocation())) {
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.protectedBlocks.contains(event.getTo())
				&& !plugin.protectedBlocks.contains(event.getFrom())) {
			if (plugin.playerBooleanCombat
					.contains(event.getPlayer().getName())) {
				event.setCancelled(true);
			}
		} else if (!plugin.protectedBlocks.contains(event.getTo())
				&& plugin.protectedBlocks.contains(event.getFrom())) {
			if (plugin.playerBooleanPreRun
					.contains(event.getPlayer().getName())) {
				plugin.playerBooleanRun.add(event.getPlayer().getName());
				if (player != player1)
					player.hidePlayer(player1);
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (plugin.protectedBlocks.contains(event.getBlock())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (plugin.protectedBlocks.contains(event.getBlock())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void PVPPreventing(EntityDamageByEntityEvent e, PlayerMoveEvent event) {
		if (plugin.playerBooleanRun.contains(event.getPlayer().getName())) {
			if (e.getDamager() instanceof Player) {
				if (e.getEntity() instanceof Player) {
					e.setCancelled(true);
				}
			}
		}
		if (plugin.protectedBlocks.contains(event.getTo())
				&& !plugin.protectedBlocks.contains(event.getFrom())) {
			if (plugin.playerBooleanCombat
					.contains(event.getPlayer().getName())) {
				event.setCancelled(true);
			}
		} else if (!plugin.protectedBlocks.contains(event.getTo())
				&& plugin.protectedBlocks.contains(event.getFrom())) {
			if (plugin.playerBooleanPreRun
					.contains(event.getPlayer().getName())) {
				plugin.playerBooleanRun.add(event.getPlayer().getName());
				plugin.playerBooleanPreRun.remove(event.getPlayer().getName());
			}
		}
		if (plugin.protectedBlocks.contains(player)) {
			if (e.getDamager() instanceof Player) {
				if (e.getEntity() instanceof Player) {
					e.setCancelled(true);
				}
			}
		}

	}

	@EventHandler
	public void PlayerRespawnEvent(PlayerRespawnEvent e) {
		if (plugin.playerBooleanCombat.contains(e.getPlayer().getName())) {
			plugin.playerBooleanPreRun.add(e.getPlayer().getName());
			plugin.playerBooleanCombat.remove(e.getPlayer().getName());
			plugin.playerBooleanRun.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void EntityDamageEvent(EntityDamageEvent e) {
		if (e.getEntity() == player) {
			e.getEntityType();
			if (EntityType.PLAYER != null)
				plugin.sm.inCombat();
		}
	}
}