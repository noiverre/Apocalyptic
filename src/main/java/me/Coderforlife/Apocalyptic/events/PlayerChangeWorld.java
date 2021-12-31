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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 *
 * @author Nick
 */
public class PlayerChangeWorld implements Listener {
    private final Main a;
    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        a.sendApocalypticTexturePack(e.getPlayer());
    }
    public PlayerChangeWorld(Main a) {
        this.a = a;
    }
}
