package com.gabezter4.asc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SCR {

	SC plugin;

	public SCR(SC instance) {
		this.plugin = instance;
	}

	int blockCounter = 0;

	public void protectArea(Player player, Location l1, Location l2) {
		int mix, max, miy, may, miz, maz;

		if (l1.getBlockX() < l2.getBlockX()) {
			mix = l1.getBlockX();
			max = l2.getBlockX();
		} else {
			mix = l2.getBlockX();
			max = l1.getBlockX();
		}
		if (l1.getBlockY() < l2.getBlockY()) {
			miy = l1.getBlockY();
			may = l2.getBlockY();
		} else {
			miy = l2.getBlockY();
			may = l1.getBlockY();
		}
		if (l1.getBlockZ() < l2.getBlockZ()) {
			miz = l1.getBlockZ();
			maz = l2.getBlockZ();
		} else {
			miz = l2.getBlockZ();
			maz = l1.getBlockZ();
		}

		for (int x = mix; x < max; x++) {
			for (int y = miy; y < may; y++) {
				for (int z = miz; z < maz; z++) {
					Location location = new Location(player.getWorld(), x, y, z);
					plugin.protectedBlocks.add(location);
					blockCounter++;
				}
			}
		}
	}
}
