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

package me.Coderforlife.Apocalyptic;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.Coderforlife.Apocalyptic.commands.ApocalypticCommandExecutor;
import me.Coderforlife.Apocalyptic.commands.HazmatCommandExecutor;
import me.Coderforlife.Apocalyptic.commands.RadiationCommandExecutor;
import me.Coderforlife.Apocalyptic.events.MonsterSpawn;
import me.Coderforlife.Apocalyptic.events.PlayerChangeWorld;
import me.Coderforlife.Apocalyptic.events.PlayerDamaged;
import me.Coderforlife.Apocalyptic.events.PlayerEat;
import me.Coderforlife.Apocalyptic.events.PlayerMove;
import me.Coderforlife.Apocalyptic.events.PlayerSpawn;
import me.Coderforlife.Apocalyptic.events.ZombieCombust;
import me.Coderforlife.Apocalyptic.events.ZombieTarget;
import me.Coderforlife.Apocalyptic.misc.ApocalypticConfiguration;
import me.Coderforlife.Apocalyptic.misc.Messages;
import me.Coderforlife.Apocalyptic.misc.RadiationManager;
import me.Coderforlife.Apocalyptic.misc.Util;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Nick,xxCoderforlife
 */
public class Apocalyptic extends JavaPlugin {
    private Logger log;
    private Random rand;
    private Plugin wg;
    private Messages messages;

    private static final String texturePack = "http://www.curseforge.com/media/files/769/14/apocalyptic_texture_pack.zip";
    
    private static final String METADATA_KEY = "radiation";

    private ItemStack hazmatHood;
    private ItemStack hazmatSuit;
    private ItemStack hazmatPants;
    private ItemStack hazmatBoots;

    private RadiationManager radiationManager;

    private ApocalypticConfiguration cachedConfig;
    private boolean recacheConfig;
    
    
    @Override
    public void onEnable(){
    	messages = new Messages(this);
        //acidRain.setCustomName("Acid Rain");
        log = getLogger();
        rand = new Random();
        wg = getWorldGuard();
        
        hazmatHood = Util.setName(new ItemStack(Material.CHAINMAIL_HELMET, 1), ChatColor.RESET + getMessages().getCaption("gasMask"));
        hazmatSuit = Util.setName(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), ChatColor.RESET + getMessages().getCaption("hazmatSuit"));
        hazmatPants = Util.setName(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), ChatColor.RESET + getMessages().getCaption("hazmatPants"));
        hazmatBoots = Util.setName(new ItemStack(Material.CHAINMAIL_BOOTS, 1), ChatColor.RESET + getMessages().getCaption("hazmatBoots"));
        
        if (wg == null) {
        }
        
        if (!getDataFolder().exists()) {
            if (!getDataFolder().mkdir()) {
                log.severe("Cannot create data folder. Expect terrible errors.");
            }
        }
        messages.saveDefault();
        if (!new File(getDataFolder().getPath() + File.separator + "config.yml").exists()) {
            saveDefaultConfig();
        }

        
        //CommandExecutors
        getCommand("radiation").setExecutor(new RadiationCommandExecutor(this));
        getCommand("apocalyptic").setExecutor(new ApocalypticCommandExecutor(this));
        getCommand("hazmat").setExecutor(new HazmatCommandExecutor(this));

        //Register Listeners
        getServer().getPluginManager().registerEvents(new PlayerSpawn(this), this);
        getServer().getPluginManager().registerEvents(new MonsterSpawn(this), this);
        getServer().getPluginManager().registerEvents(new PlayerEat(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDamaged(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChangeWorld(this), this);
        getServer().getPluginManager().registerEvents(new ZombieTarget(this), this);
        getServer().getPluginManager().registerEvents(new ZombieCombust(this), this);
        
    }
 
    @Override
    public void onDisable() {
    }
    /**
     * 
     * @param name of the world
     * @return whether the named world has fallout enabled
     */
    public boolean worldEnabledFallout(String name) {
        return getConfig().getConfigurationSection("worlds").getKeys(false).contains(name) && getConfig().getBoolean("worlds." + name + ".fallout"); //$NON-NLS-3$
    }
    /**
     * 
     * @param name of the world
     * @return whether the named world has zombie apocalypse enabled
     */
    public boolean worldEnabledZombie(String name) {
        return getConfig().getConfigurationSection("worlds").getKeys(false).contains(name) && getConfig().getBoolean("worlds." + name + ".zombie"); //$NON-NLS-3$
    }
    /**
     * 
     * @param p The player
     * @return whether the player has a full hazmat suit
     */
    public boolean playerWearingHazmatSuit(Player p) {
        EntityEquipment e = p.getEquipment();
        boolean helmet =  e.getHelmet() != null && e.getHelmet().hasItemMeta() && e.getHelmet().getItemMeta().hasDisplayName() && (e.getHelmet().getItemMeta().getDisplayName().startsWith(ChatColor.RESET + getMessages().getCaption("gasMask")) || e.getHelmet().getItemMeta().getDisplayName().startsWith(ChatColor.RESET + getMessages().getCaption("hazmat")));
        boolean chest =  e.getChestplate() != null && e.getChestplate().hasItemMeta() && e.getChestplate().getItemMeta().hasDisplayName() && e.getChestplate().getItemMeta().getDisplayName().startsWith(ChatColor.RESET + getMessages().getCaption("hazmat"));
        boolean legs =  e.getLeggings() != null && e.getLeggings().hasItemMeta() && e.getLeggings().getItemMeta().hasDisplayName() && e.getLeggings().getItemMeta().getDisplayName().startsWith(ChatColor.RESET + getMessages().getCaption("hazmat"));
        boolean boots =  e.getBoots() != null && e.getBoots().hasItemMeta() && e.getBoots().getItemMeta().hasDisplayName() && e.getBoots().getItemMeta().getDisplayName().startsWith(ChatColor.RESET + getMessages().getCaption("hazmat"));
        return helmet && chest && legs && boots;
    }

    
    /**
     * Sends apocalyptic texture pack to a player.
     * @param p the player which to send the texture pack to
     */
    public void sendApocalypticTexturePack(Player p) {
        if (!getConfig().getBoolean("worlds."+p.getWorld().getName() + ".texturepack")) {
            return;
        }
        p.setResourcePack(texturePack);
    }
    @Override
    public ApocalypticConfiguration getConfig() {
        if (cachedConfig == null || recacheConfig) {
            recacheConfig = false;
            ApocalypticConfiguration config = new ApocalypticConfiguration();
            try {
                config.load(new File(getDataFolder().getPath() + File.separator + "config.yml"));
            } catch (IOException | InvalidConfigurationException ex) {
                ex.printStackTrace();
            }
            cachedConfig = config;
            return config;
        }
        else {
            return cachedConfig;
        }
    }
    /**
     * 
     * @param p a player
     * @param cmd String alias of a command
     * @return whether the specified player can perform the command
     */
    public boolean canDoCommand(CommandSender p, String cmd) {
    	if (p == getServer().getConsoleSender()) {
    		return true;
    	}
    	boolean usePerms = getConfig().getBoolean("meta.permissions");
    	if (usePerms) {
    		return (cmd.equals("radiation.self") && p.hasPermission("apocalyptic.radiation.self")) ||
    				(cmd.equals("radiation.other") && p.hasPermission("apocalyptic.radiation.other")) ||
    				(cmd.equals("radiation.change") && p.hasPermission("apocalyptic.radiation.change.self"))  ||
    				(cmd.equals("apocalyptic.radhelp") && p.hasPermission("apocalyptic.help.radiation")) ||
    				(cmd.equals("apocalyptic.stop") && p.hasPermission("apocalyptic.admin.stop")) ||
    				(cmd.equals("apocalyptic.reload") && p.hasPermission("apocalyptic.admin.reload") ||
                    (cmd.equals("hazmatArmor.self") && p.hasPermission("apocalyptic.hazmatArmor.self")) ||
                    (cmd.equals("hazmatArmor.other") && p.hasPermission("apocalyptic.hazmatArmor.other")));
    	}
    	else {
            return !(cmd.equals("radiation.other") || cmd.equals("radiation.change") || cmd.equals("apocalyptic.stop") || cmd.equals("apocalyptic.reload") || cmd.equals("hazmatArmor.self") || cmd.equals("hazmatArmor.other")) || p.isOp();
        }
    }
    private Plugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
     
        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }
     
        return plugin;
    }

    /**
     * Get the language file.
     * @return The language file.
     */
	public Messages getMessages() {
		return messages;
	}

    /**
     * Recache the configuration file.
     */
    public void reloadConfig() {
        recacheConfig = true;
    }

    /**
     * Get the metadata key used to save radiation to players.
     * @return The metadata key.
     */
    public String getMetadataKey() {
        return METADATA_KEY;
    }

    /**
     * Get the radiation manager, used for saving, adding, and setting radiation.
     * @return The Radiation Manager object.
     */
    public RadiationManager getRadiationManager() {
        return radiationManager;
    }

    /**
     * Get the Gas Mask itemstack
     * @return an itemstack with 1 Gas Mask.
     */
    public ItemStack getHazmatHood() {
        return hazmatHood;
    }
    /**
     * Get the Hazmat Suit itemstack
     * @return an itemstack with 1 Hazmat Suit.
     */
    public ItemStack getHazmatSuit() {
        return hazmatSuit;
    }
    /**
     * Get the Hazmat Leggings itemstack
     * @return an itemstack with 1 Hazmat Leggings.
     */
    public ItemStack getHazmatPants() {
        return hazmatPants;
    }
    /**
     * Get the Hazmat Boots itemstack
     * @return an itemstack with 1 Hazmat Boots.
     */
    public ItemStack getHazmatBoots() {
        return hazmatBoots;
    }
    public Random getRandom() {
        return rand;
    }
}
