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
import java.util.Random;
import java.util.logging.Logger;

import com.firegnom.valkyrie.server.gamemode.GameMode;
import com.firegnom.valkyrie.server.map.Zone;
import com.firegnom.valkyrie.server.tasks.MoverTask;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;

// TODO: Auto-generated Javadoc
/**
 * The Class MobPlayer.
 */
public class MobPlayer extends Player {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MobPlayer.class
			.getName());

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;

	/** The player class. */
	private int playerClass;

	/** The move range. */
	private int moveRange;

	/** The mode. */
	private GameMode mode;

	/** The zone. */
	private ManagedReference<Zone> zone;

	/**
	 * Instantiates a new mob player.
	 * 
	 * @param name
	 *            the name
	 */
	public MobPlayer(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#getMode()
	 */
	@Override
	public GameMode getMode() {
		return mode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#getPlayerClass()
	 */
	@Override
	public int getPlayerClass() {
		return playerClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#getZone()
	 */
	@Override
	public Zone getZone() {
		return zone.get();
	}


	/**
	 * Sets the zone.
	 * 
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(Zone zone) {
		this.zone = AppContext.getDataManager().createReference(zone);
	}

	/**
	 * Sets the mode.
	 * 
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(GameMode mode) {
		this.mode = mode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#send(java.nio.ByteBuffer)
	 */
	@Override
	public void send(ByteBuffer bb) {

	}

	/**
	 * Sets the player class.
	 * 
	 * @param playerClass
	 *            the playerClass to set
	 */
	public void setPlayerClass(int playerClass) {
		this.playerClass = playerClass;
	}

	/**
	 * Start ai.
	 */
	public void startAI() {
		Random r = new Random();
		AppContext.getTaskManager().schedulePeriodicTask(
				new MoverTask(this, this.zone.get().getZoneMap()),
				5000 + r.nextInt(10000), 15000 + r.nextInt(10000));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#listen(int)
	 */
	@Override
	public boolean listen(int flag) {
		switch (flag) {
		case LISTEN_MOVES:
			return false;
		}
		return false;
	}

	
	/**
	 * Sets the move range.
	 * 
	 * @param moveRange
	 *            the moveRange to set
	 */
	public void setMoveRange(int moveRange) {
		this.moveRange = moveRange;
	}

	/**
	 * Gets the move range.
	 * 
	 * @return the moveRange
	 */
	public int getMoveRange() {
		return moveRange;
	}

}
