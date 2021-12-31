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
		ItemStack item1 = new ItemStack(Material.SMALL_AMETHYST_BUD, 2);
		ItemStack item2 = new ItemStack(Material.MEDIUM_AMETHYST_BUD, 2);
		ItemStack item3 = new ItemStack(Material.LARGE_AMETHYST_BUD, 1);
		ItemStack item4 = new ItemStack(Material.EXPERIENCE_BOTTLE, 3);
		ItemStack item5 = new ItemStack(Material.LAPIS_LAZULI, 2);
		ItemStack item6 = new ItemStack(Material.BONE, 1);
		ItemStack item7 = new ItemStack(Material.MELON_SEEDS, 1);
		ItemStack item8 = new ItemStack(Material.REDSTONE, 1);
		ItemStack item9 = new ItemStack(Material.IRON_NUGGET, 1);
		ItemStack item10 = new ItemStack(Material.GOLD_NUGGET, 1);
		ItemStack item11 = new ItemStack(Material.AMETHYST_CLUSTER, 1);
		ItemStack item12 = new ItemStack(Material.BUDDING_AMETHYST, 1);
		ItemStack item13 = new ItemStack(Material.FEATHER, 1);
		ItemStack item14 = new ItemStack(Material.SPIDER_EYE, 1);
		ItemStack item15 = new ItemStack(Material.GUNPOWDER, 1);
		ItemStack item16 = new ItemStack(Material.AMETHYST_SHARD);
		ItemStack item17 = new ItemStack(Material.COCOA_BEANS);
		LivingEntity livingEntity = e.getEntity();
		if (e.getEntity() instanceof Zombie || e.getEntity() instanceof Drowned || e.getEntity() instanceof Stray || e.getEntity() instanceof Husk) {
			Random r = new Random();
			int dropChances = r.nextInt(30);
			switch (dropChances) {
			case 1:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item1);
				break;
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
			case 8:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item8);
				break;
			case 9:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item9);
				break;
			case 10:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item10);
				break;
			case 11:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item11);
				break;
			case 12:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item12);
				break;
			case 13:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item13);
				break;
			case 14:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item14);
				break;
			case 15:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item15);
				break;
			case 16:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item16);
				break;
			case 17:
				livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), item17);
				break;
			}
		}
	}
}
