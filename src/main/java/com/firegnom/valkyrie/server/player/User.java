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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.firegnom.valkyrie.net.protocol.helper.Protocol;
import com.firegnom.valkyrie.server.gamemode.GameMode;
import com.firegnom.valkyrie.server.gamemode.LoggedOutMode;
import com.firegnom.valkyrie.server.map.Zone;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.ObjectNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User implements ClientSessionListener, ManagedObject,
		Serializable, Player {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(User.class.getName());

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The username. */
	private String username;

	// put position in to the different class and only keep reference
	/** The position. */
	private Point position;

	/** The player class. */
	public int playerClass;

	/** The mode. */
	private GameMode mode;

	/** The session ref. */
	private ManagedReference<? extends ClientSession> sessionRef;

	/** The zone. */
	private ManagedReference<Zone> zone;

	/** The created. */
	private boolean created = false;

	/**
	 * Instantiates a new user.
	 * 
	 * @param username
	 *            the username
	 */
	public User(String username) {
		logger.log(Level.INFO, "User : {0} created:", username);
		this.username = username;
	}

	/**
	 * Sets the session.
	 * 
	 * @param session
	 *            the new session
	 */
	public void setSession(ClientSession session) {
		// check if u are logged in already;
		sessionRef = AppContext.getDataManager().createReference(session);
	}

	/**
	 * Change mode.
	 * 
	 * @param m
	 *            the m
	 */
	public void changeMode(GameMode m) {
		logger.log(Level.INFO, "chamgeing game mode to :{0}", m.getType());
		if (mode != null) {
			mode.leave();
		}
		this.mode = m;
		m.join(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.sgs.app.ClientSessionListener#disconnected(boolean)
	 */
	@Override
	public void disconnected(boolean arg0) {
		changeMode(new LoggedOutMode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.sgs.app.ClientSessionListener#receivedMessage(java.nio.ByteBuffer
	 * )
	 */
	@Override
	public void receivedMessage(ByteBuffer arg0) {
		logger.log(Level.INFO, "User : {0} recieaved message:", username);
		Protocol protocol = new Protocol(mode);
		protocol.decode(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#getPosition()
	 */
	@Override
	public Point getPosition() {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.server.player.Player#setPosition(com.firegnom.valkyrie
	 * .util.Point)
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#send(java.nio.ByteBuffer)
	 */
	@Override
	public void send(ByteBuffer bb) {
		ClientSession clientSession;
		try {
			clientSession = sessionRef.get();
		} catch (ObjectNotFoundException e) {
			logger.log(Level.INFO, "My mode should be 2 and is :{0}",
					mode.getType());
			return;
		}
		clientSession.send(bb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#getName()
	 */
	@Override
	public String getName() {
		return username;
	}

	/**
	 * Checks if is created.
	 * 
	 * @return true, if is created
	 */
	public boolean isCreated() {
		return created;
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

	/**
	 * Sets the created.
	 * 
	 * @param b
	 *            the new created
	 */
	public void setCreated(boolean b) {
		created = b;
	}

	/**
	 * Sets the zone.
	 * 
	 * @param zone
	 *            the new zone
	 */
	public void setZone(Zone zone) {
		if (this.zone != null) {
			this.zone.get().leave(this);
		}
		this.zone = AppContext.getDataManager().createReference(zone);
		zone.join(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.player.Player#getZone()
	 */
	public Zone getZone() {
		return zone.get();
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
	 * @see com.firegnom.valkyrie.server.player.Player#listen(int)
	 */
	public boolean listen(int flag) {
		switch (flag) {
		case LISTEN_MOVES:
			return true;
		}
		return false;
	}

}
