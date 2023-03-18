/*
 * Copyright (C) 2014 Nick Schatz
 *
 *     This file is part of Apocalyptic.
 *
 *     Apocalyptic is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Apocalyptic is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Apocalyptic.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.Coderforlife.Apocalyptic.events;

import me.Coderforlife.Apocalyptic.*;
<<<<<<< HEAD

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;

import me.Coderforlife.Apocalyptic.misc.ZombieHelper;
=======
import me.Coderforlife.Apocalyptic.misc.*;
import me.Coderforlife.Apocalyptic.events.HardDespawn;
import net.md_5.bungee.api.ChatColor;
>>>>>>> parent of 2c8986b (revert changes in previous commit)

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.attribute.Attribute;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.player.PlayerMoveEvent;



/**
 *
 * @author Nick
 */
public class MonsterSpawn implements Listener {
    private final Main a;
<<<<<<< HEAD


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMonsterSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            int spawnLimit = 256;
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            int spawnPerPlayer = (int) Math.ceil(spawnLimit / onlinePlayers);

            // If there's only one player, make sure at least 16 zombie is spawned
            if (onlinePlayers == 1) {
                spawnPerPlayer = 16;
            }

            // Create a map to store the number of spawns for each player
            HashMap<Player, Integer> spawnCount = new HashMap<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                spawnCount.put(player, 0);
=======
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMonsterSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            int spawnLimit = 256;
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            int spawnPerPlayer = (int) Math.ceil(spawnLimit / (double) onlinePlayers);

            // If there's only one player, make sure at least 16 zombie is spawned
            if (onlinePlayers == 1 && spawnPerPlayer == 0) {
                spawnPerPlayer = 16;
>>>>>>> parent of 2c8986b (revert changes in previous commit)
            }

            // Create a map to store the number of spawns for each player
            HashMap<Player, Integer> spawnCount = new HashMap<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                spawnCount.put(player, 0);
            }

            // Spawn zombies for each player
            for (int i = 0; i < spawnPerPlayer; i++) {
                Player leastSpawnedPlayer = null;
                int leastSpawnCount = Integer.MAX_VALUE;

                // Find the player with the least spawns
                for (Player player : spawnCount.keySet()) {
                    int count = spawnCount.get(player);
                    if (count < leastSpawnCount) {
                        leastSpawnedPlayer = player;
                        leastSpawnCount = count;
                    }
                }

                // Increment the spawn count for the player
                int count = spawnCount.get(leastSpawnedPlayer);
                spawnCount.put(leastSpawnedPlayer, count + 1);

                // Generate the spawn location
                Location spawnLocation = getSpawnLocation(leastSpawnedPlayer);

                // Spawn the zombie if the location is valid
                if (canZombieSpawn(spawnLocation)) {
                    event.getLocation().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
                }
            }
        }
    }

<<<<<<< HEAD

=======
>>>>>>> parent of 2c8986b (revert changes in previous commit)
    private Location getSpawnLocation(Player player) {
        int spawnRadius = 16;
        int spawnX = player.getLocation().getBlockX() + (int) (Math.random() * spawnRadius * 2) - spawnRadius;
        int spawnZ = player.getLocation().getBlockZ() + (int) (Math.random() * spawnRadius * 2) - spawnRadius;
        Location spawnLocation = new Location(player.getWorld(), spawnX, 0, spawnZ);
        int spawnY = player.getWorld().getHighestBlockYAt(spawnLocation);
        spawnLocation.setY(spawnY);
        return spawnLocation;
    }

    private boolean canZombieSpawn(Location location) {
        Block block = location.getBlock();
        Block above = block.getRelative(BlockFace.UP);
        return block.getType() == Material.AIR && above.getType() == Material.AIR;
    }
<<<<<<< HEAD

=======
>>>>>>> parent of 2c8986b (revert changes in previous commit)
    public MonsterSpawn(Main a) {
        this.a = a;
    }
}
