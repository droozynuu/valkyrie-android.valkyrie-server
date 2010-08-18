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
