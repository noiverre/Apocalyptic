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

import me.Coderforlife.Apocalyptic.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class RadiationManager {
    private final Main apocalyptic;
    public RadiationManager(Main apocalyptic) {
        this.apocalyptic = apocalyptic;
    }

    /**
     *
     * @param p the player which to add radiation to
     * @param level the amount of radiation (in grays) to add to the player
     */
    public void addPlayerRadiation(Player p, double level) {

        //p.setMetadata(apocalyptic.getMetadataKey(), new FixedMetadataValue(p.getMetadata(apocalyptic.getMetadataKey()).g));
        double oldRadiation = 0;
        if (p.getMetadata(apocalyptic.getMetadataKey()).size() > 0) {
            oldRadiation = p.getMetadata(apocalyptic.getMetadataKey()).get(0).asDouble();
            p.setMetadata(apocalyptic.getMetadataKey(), new FixedMetadataValue(apocalyptic, oldRadiation+level));
        }
        else {
            p.setMetadata(apocalyptic.getMetadataKey(), new FixedMetadataValue(apocalyptic, level));
        }

        if (getPlayerRadiation(p) >= 0.8 && getPlayerRadiation(p) < 1.0) {
            p.sendMessage(new String[] {
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("radiationCriticalWarning") ,
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("radBloodWarning") });
        }
        if (oldRadiation < 1.0 && getPlayerRadiation(p) >= 1.0 && getPlayerRadiation(p) < 6.0) {
            p.sendMessage(new String[] {
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("radDangerLevel") ,
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("radBlood") ,
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("takemoredamage")
            });
        }
        if (oldRadiation < 6.0 && getPlayerRadiation(p) >= 6.0 && getPlayerRadiation(p) < 10.0) {
            p.sendMessage(new String[] {
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("radiationCritical") ,
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("radBloodStomach") ,
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("takeMoreDamageandNoEat")
            });
        }
        if (oldRadiation < 10.0 && getPlayerRadiation(p) >= 10) {
            p.sendMessage(new String[] {
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("radDeadly") ,
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("radAll") ,
                    ChatColor.RED + apocalyptic.getMessages().getCaption("warning") +" "+ ChatColor.GOLD + apocalyptic.getMessages().getCaption("radAllExplain")
            });
        }

    }
    /**
     *
     * @param p the player
     * @return the radiation level (in grays) of the specified player
     */
    public double getPlayerRadiation(Player p) {
        if (p.getMetadata(apocalyptic.getMetadataKey()).size() > 0) {
            return p.getMetadata(apocalyptic.getMetadataKey()).get(0).asDouble();
        }
        return 0;
    }
    /**
     *
     * @param p the player which to set the radiation level of
     * @param radiation the level of radiation (in grays) that the player is set to
     */
    public void setPlayerRadiation(Player p, double radiation) {
        addPlayerRadiation(p, getPlayerRadiation(p) * -1);
        addPlayerRadiation(p, radiation);
    }
}
