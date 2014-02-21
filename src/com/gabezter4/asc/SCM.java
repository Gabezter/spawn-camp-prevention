package com.gabezter4.asc;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SCM {

	Player player;
	public void sendConsole(String s){
		System.out.println(s);
	}
	
	public String capitaliseFristLetter(String s){
		s = s.toLowerCase();
		char[] stringArray = s.toCharArray();
		stringArray[0] = Character.toUpperCase(stringArray[0]);
		s = new String(stringArray);
		return s;
	}
	
	public void sendMessage(Player player, String s){
		player.sendMessage(ChatColor.DARK_RED + "[Hotel Manager]" + ChatColor.GRAY + s);
	}
	
	public String showBlockCoords(Location l){
		return ChatColor.LIGHT_PURPLE + "" + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ();
	}	
}
