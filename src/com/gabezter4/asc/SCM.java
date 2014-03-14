package com.gabezter4.asc;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SCM {

	Player player;
	SC plugin;

	public void sendConsole(String s) {
		System.out.println(s);
	}

	public String capitaliseFristLetter(String s) {
		s = s.toLowerCase();
		char[] stringArray = s.toCharArray();
		stringArray[0] = Character.toUpperCase(stringArray[0]);
		s = new String(stringArray);
		return s;
	}

	public void sendMessage(Player player, String s) {
		plugin.player.sendMessage(ChatColor.DARK_RED + "[Hotel Manager]"
				+ ChatColor.GRAY + s);
	}

	public String showBlockCoords(Location l) {
		return ChatColor.LIGHT_PURPLE + "" + l.getBlockX() + ", "
				+ l.getBlockY() + ", " + l.getBlockZ();
	}

	boolean isRunning() {
		plugin.player.sendMessage(ChatColor.DARK_BLUE
				+ "You are now in Running Mode!!!");
		plugin.player.sendMessage(ChatColor.DARK_BLUE + "PVP is now disabled for you.");
		plugin.player.hidePlayer(player);
		return true;
	}

	boolean inCombat() {
		plugin.player.sendMessage(ChatColor.DARK_RED + "You are now in Combat!!!");
		plugin.run();
		return true;
	}

	boolean notRunning() {
		plugin.playerBooleanRun.remove(plugin.player.getName());
		plugin.player.sendMessage(ChatColor.DARK_BLUE
				+ "You are now out of Running Mode!!!");
		plugin.player.sendMessage(ChatColor.DARK_BLUE + "PVP is now enabled for you.");
		plugin.player.showPlayer(player);
		return true;
	}

	boolean notCombat() {
		plugin.playerBooleanCombat.remove(plugin.player.getName());
		plugin.player.sendMessage(ChatColor.DARK_GREEN
				+ "You are now out of Combat!!!");
		return true;
	}
}
