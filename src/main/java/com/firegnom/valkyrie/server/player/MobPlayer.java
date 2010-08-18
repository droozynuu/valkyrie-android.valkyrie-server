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
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;

// TODO: Auto-generated Javadoc
/**
 * The Class MobPlayer.
 */
public class MobPlayer implements Player, ManagedObject, Serializable {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MobPlayer.class
			.getName());

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;

	// look to user
	/** The start position. */
	private Point startPosition;

	/** The position. */
	private Point position;

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
	 * @see com.firegnom.valkyrie.server.player.Player#getPosition()
	 */
	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return position;
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
	 * Sets the position.
	 * 
	 * @param position
	 *            the position to set
	 */

	public void setPosition(Point position) {
		this.position = position;
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
		// TODO Auto-generated method stub

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
	 * Sets the start position.
	 * 
	 * @param startPosition
	 *            the startPosition to set
	 */
	public void setStartPosition(Point startPosition) {
		this.startPosition = startPosition;
		this.setPosition(startPosition);
	}

	/**
	 * Gets the start position.
	 * 
	 * @return the startPosition
	 */
	public Point getStartPosition() {
		return startPosition;
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
