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
package com.firegnom.valkyrie.server.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.firegnom.valkyrie.server.player.Player;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;

// TODO: Auto-generated Javadoc
/**
 * The Class Zone.
 */
public class Zone implements Serializable, ManagedObject {

	/** The zone map. */
	private ZoneMap zoneMap;

	/** The name. */
	private String name;

	/** The shout range. */
	int shoutRange = 50;

	/** The users. */
	private List<ManagedReference<Player>> users;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new zone.
	 * 
	 * @param name
	 *            the name
	 */
	public Zone(String name) {
		this.name = name;
		users = new ArrayList<ManagedReference<Player>>();
	}

	/**
	 * Join.
	 * 
	 * @param user
	 *            the user
	 */
	public void join(Player user) {
		users.add(AppContext.getDataManager().createReference(user));
	}

	/**
	 * Leave.
	 * 
	 * @param user
	 *            the user
	 */
	public void leave(Player user) {
		users.remove(AppContext.getDataManager().createReference(user));
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;

	}

	/**
	 * Gets the players in range.
	 * 
	 * @param user
	 *            the user
	 * @param goTo
	 *            the go to
	 * @param range
	 *            the range
	 * @param mapMode
	 *            the map mode
	 * @return the players in range
	 */
	public HashSet<Player> getPlayersInRange(Player user, Point goTo,
			int range, int mapMode) {
		HashSet<Player> ret = new HashSet<Player>();
		Point start = user.getPosition();
		for (ManagedReference<Player> u : users) {
			Player us = u.get();
			if (us.listen(Player.LISTEN_MOVES)
					&& (!us.getName().equals(user.getName()))
					&& (us.getMode().getType() == mapMode)
					&& (us.getPosition().inRange(start, range) || us
							.getPosition().inRange(goTo, range))) {
				ret.add(us);
			}
		}
		return ret;
	}

	/**
	 * Gets the players in range.
	 * 
	 * @param user
	 *            the user
	 * @param range
	 *            the range
	 * @return the players in range
	 */
	public HashSet<Player> getPlayersInRange(Player user, int range) {
		return getPlayersInRange(user, range, null);
	}

	/**
	 * Gets the players in range.
	 * 
	 * @param user
	 *            the user
	 * @param range
	 *            the range
	 * @param append
	 *            the append
	 * @return the players in range
	 */
	public HashSet<Player> getPlayersInRange(Player user, int range,
			HashSet<Player> append) {
		Point p = user.getPosition();
		HashSet<Player> ret;
		if (append == null) {
			ret = new HashSet<Player>();
		} else {
			ret = append;
		}
		for (ManagedReference<Player> u : users) {
			Player us = u.get();
			if (us.getPosition().inRange(p, range)
					&& !us.getName().equals(user.getName())) {
				ret.add(us);
			}
		}
		return ret;
	}

	/**
	 * Sets the zone map.
	 * 
	 * @param zoneMap
	 *            the zoneMap to set
	 */
	public void setZoneMap(ZoneMap zoneMap) {
		this.zoneMap = zoneMap;
	}

	/**
	 * Gets the zone map.
	 * 
	 * @return the zoneMap
	 */
	public ZoneMap getZoneMap() {
		return zoneMap;
	}

}
