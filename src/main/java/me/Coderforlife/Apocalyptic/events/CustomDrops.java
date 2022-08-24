package me.Coderforlife.Apocalyptic.events;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Stray;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.Coderforlife.Apocalyptic.Main;

public class CustomDrops implements Listener {
	
	public List<String> mobs;
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public CustomDrops(Main pl) {
		this.plugin = pl;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
		
	}


	@EventHandler
	public void on(EntityDeathEvent e) {
		ItemStack item2 = new ItemStack(Material.BONE, 1);
		ItemStack item3 = new ItemStack(Material.MELON_SEEDS, 1);
		ItemStack item4 = new ItemStack(Material.REDSTONE, 1);
		ItemStack item5 = new ItemStack(Material.GOLD_NUGGET, 1);
		ItemStack item6 = new ItemStack(Material.FEATHER, 1);
		ItemStack item7 = new ItemStack(Material.SPIDER_EYE, 1);
		ItemStack item9 = new ItemStack(Material.COCOA);
		LivingEntity livingEntity = e.getEntity();
		if (e.getEntity() instanceof Zombie || e.getEntity() instanceof Drowned || e.getEntity() instanceof Stray || e.getEntity() instanceof Husk) {
			Random r = new Random();
			int dropChances = r.nextInt(30);
			switch (dropChances) {
			case 2:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item2);
				break;
			case 3:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item3);
				break;
			case 4:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item4);
				break;
			case 5:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item5);
				break;
			case 6:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item6);
				break;
			case 7:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item7);
				break;
			case 9:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item9);
				break;
			}
		}
	}
}
