package me.Coderforlife.Apocalyptic.events;

import me.Coderforlife.Apocalyptic.*;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class HardDespawn implements Listener {
    private final Main a;
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        World world = player.getWorld();
        int despawnDistance = 32;

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Zombie) {
                Location zombieLocation = entity.getLocation();
                double distance = playerLocation.distance(zombieLocation);

                if (distance > despawnDistance) {
                    entity.remove();
                }
            }
        }
    }
    public HardDespawn(Main a) {
        this.a = a;
    }
}
