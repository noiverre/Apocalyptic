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

package me.Coderforlife.Apocalyptic.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Coderforlife.Apocalyptic.Main;

public class Messages {
	private Main plugin = null;
	 
	private File configFile = null;
	private FileConfiguration config = null;
	 
	public Messages(Main plugin)
	{
		this.plugin = plugin;
		this.configFile = new File(this.plugin.getDataFolder(), "lang.yml");
		 
		this.saveDefault();
		
	}
	 
	public void saveDefault()
	{
		if (!this.configFile.exists())
		{
			this.plugin.saveResource("lang.yml", false);
		}
		reload();
		
		
	}
	public void reload() {
		config = new YamlConfiguration();
		try {
			config.load(configFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public String getCaption(String name)
	{
		return this.getCaption(name, false);
	}
	 
	public String getCaption(String name, boolean color)
	{
		String caption = this.config.getString(name);
		if (caption == null)
		{
		this.plugin.getLogger().warning("Missing caption: " + name);
		caption = "&c[missing caption]";
		}
		 
		if (color)
		{
			caption = ChatColor.translateAlternateColorCodes('&', caption);
		}
		return caption;
	}
}
