/*******************************************************************************
 * Copyright (c) 2010 Maciej Kaniewski (mk@firegnom.com).
 *  
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 3 of the License, or
 *     (at your option) any later version.
 *  
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *  
 *     You should have received a copy of the GNU General Public License
 *     along with this program; if not, write to the Free Software Foundation,
 *     Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 *  
 *     Contributors:
 *      Maciej Kaniewski (mk@firegnom.com) - initial API and implementation
 ******************************************************************************/
/*
 * 
 */
package com.firegnom.valkyrie.server.helpers;

import com.firegnom.valkyrie.server.map.Zone;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.NameNotBoundException;
import com.sun.sgs.app.ObjectNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class ZoneHelper.
 */
public class ZoneHelper {

	// map all users to a known namespace
	/** The Constant NAME_PREFIX. */
	private static final String NAME_PREFIX = "valkyrie:zone:";

	/**
	 * Returns the player, or null if there is no player instance.
	 * 
	 * @param name
	 *            the name
	 * @return the zone
	 */
	public static Zone getZone(String name) {
		try {
			return (Zone) AppContext.getDataManager().getBinding(
					NAME_PREFIX + name);
		} catch (NameNotBoundException nnbe) {
			return null;
		} catch (ObjectNotFoundException onfe) {
			return null;
		}
	}

	/**
	 * Adds a player to the set of known players.
	 * 
	 * @param zone
	 *            the zone
	 */
	public static void addZone(Zone zone) {
		String name = zone.getName();
		if (getZone(name) != null) {
			throw new IllegalArgumentException("Player already exists: " + name);
		}
		AppContext.getDataManager().setBinding(NAME_PREFIX + name, zone);
	}

}
