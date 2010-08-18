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

import java.nio.ByteBuffer;

import com.firegnom.valkyrie.server.gamemode.GameMode;
import com.firegnom.valkyrie.server.map.Zone;
import com.firegnom.valkyrie.util.Point;

// TODO: Auto-generated Javadoc
/**
 * The Interface Player.
 */
public interface Player {

	/** The Constant LISTEN_MOVES. */
	public static final int LISTEN_MOVES = 0;

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	public Point getPosition();

	/**
	 * Sets the position.
	 * 
	 * @param p
	 *            the new position
	 */
	public void setPosition(Point p);

	/**
	 * Gets the mode.
	 * 
	 * @return the mode
	 */
	public GameMode getMode();

	/**
	 * Gets the player class.
	 * 
	 * @return the player class
	 */
	public int getPlayerClass();

	/**
	 * Gets the zone.
	 * 
	 * @return the zone
	 */
	public Zone getZone();

	/**
	 * Send.
	 * 
	 * @param bb
	 *            the bb
	 */
	public void send(ByteBuffer bb);

	/**
	 * Listen.
	 * 
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public boolean listen(int value);

}
