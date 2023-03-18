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
import me.Coderforlife.Apocalyptic.misc.ZombieHelper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.Bukkit;

/**
 *
 * @author Nick
 */
public class MonsterSpawn implements Listener {
    private final Main a;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMonsterSpawn(CreatureSpawnEvent e) {

        if (e.getEntityType() == EntityType.ZOMBIE && a.worldEnabledZombie(e.getLocation().getWorld().getName())) {
            if (e.getEntity().getWorld().getEntitiesByClass(Zombie.class).size() >=
                    a.getConfig().getWorld(e.getLocation().getWorld()).getInt("mobs.zombies.spawnLimit")) {
                e.setCancelled(true);
                return;
            }

            Location l = e.getLocation();
            Zombie zom = (Zombie) e.getEntity();
            zom.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(a.getConfig().getWorld(e.getEntity().getWorld()).getDouble("mobs.zombies.max-health"));

            if (e.getSpawnReason() != SpawnReason.CUSTOM && e.getSpawnReason() != SpawnReason.SPAWNER) {
                int hordeSize = a.getRandom().nextInt(
                        a.getConfig().getWorld(e.getEntity().getWorld()).getInt("mobs.zombies.hordeSize.max") -
                                a.getConfig().getWorld(e.getEntity().getWorld()).getInt("mobs.zombies.hordeSize.min")) +
                        a.getConfig().getWorld(e.getEntity().getWorld()).getInt("mobs.zombies.hordeSize.min");
                int failedAttempts = 0;

                // distribute zombies evenly among online players
                Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
                int numPlayers = players.length;
                int zombiesPerPlayer = hordeSize / numPlayers;
                int extraZombies = hordeSize % numPlayers;
                int index = 0;

                for (int i = 0; i < numPlayers; i++) {
                    int playerZombies = zombiesPerPlayer;
                    if (extraZombies > 0) {
                        playerZombies++;
                        extraZombies--;
                    }
                    Player player = players[index];
                    index = (index + 1) % numPlayers;

                    for (int j = 0; j < playerZombies; j++) {
                        // TODO make point selection better
                        int spotX = 7 - a.getRandom().nextInt(14);
                        int spotZ = 7 - a.getRandom().nextInt(14);
                        //int spotY = 3-a.getRandom().nextInt(6);
                        Location spawnPoint = l.add(spotX, 0 /*spotY*/, spotZ);
                        spawnPoint.setY(l.getWorld().getHighestBlockYAt(spotX, spotZ));
                        if (!ZombieHelper.canZombieSpawn(spawnPoint) && failedAttempts <= 10) {
                            failedAttempts++;
                            continue;
                        }
                        failedAttempts = 0;
                        Zombie zombie = (Zombie) l.getWorld().spawnEntity(spawnPoint, EntityType.ZOMBIE);
                    }
                }
            }
        }
    }

    public MonsterSpawn(Main a) {
        this.a = a;
    }
}