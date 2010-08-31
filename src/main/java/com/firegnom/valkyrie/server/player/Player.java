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
package com.firegnom.valkyrie.server.player;

import java.io.Serializable;
import java.nio.ByteBuffer;

import com.firegnom.valkyrie.server.gamemode.GameMode;
import com.firegnom.valkyrie.server.map.Zone;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;


// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public abstract class Player implements  ManagedObject, Serializable {

	/** The position. */
	private ManagedReference<PlayerPosition> position;

	/** The Constant LISTEN_MOVES. */
	public static final int LISTEN_MOVES = 0;

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	public ManagedReference<PlayerPosition> getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 * 
	 * @param p
	 *            the new position
	 */
	public void setPosition(PlayerPosition p) {
		if (position != null) {
			AppContext.getDataManager().removeObject(position.getForUpdate());
		}
		position = AppContext.getDataManager().createReference(p);
	}

	/**
	 * Gets the mode.
	 * 
	 * @return the mode
	 */
	public abstract GameMode getMode();

	/**
	 * Gets the player class.
	 * 
	 * @return the player class
	 */
	public abstract int getPlayerClass();

	/**
	 * Gets the zone.
	 * 
	 * @return the zone
	 */
	public abstract Zone getZone();

	/**
	 * Send.
	 * 
	 * @param bb
	 *            the bb
	 */
	public abstract void send(ByteBuffer bb);

	/**
	 * Listen.
	 * 
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public abstract boolean listen(int value);

}
