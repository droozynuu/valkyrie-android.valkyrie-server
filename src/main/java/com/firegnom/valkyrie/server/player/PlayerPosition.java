package com.firegnom.valkyrie.server.player;

import java.io.Serializable;

import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.ManagedObject;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerPosition.
 */
public class PlayerPosition implements Serializable, ManagedObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The position. */
	private Point position;
	
	/**
	 * Instantiates a new player position.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public PlayerPosition(int x, int y) {
		position = new Point(x, y);
	}
	
	/**
	 * Instantiates a new player position.
	 *
	 * @param point the point
	 */
	public PlayerPosition(Point point) {
		this.position = point;
	}
	
	/**
	 * Sets the position.
	 *
	 * @param point the new position
	 */
	public void setPosition(Point point) {
		this.position = point;
	}
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

}
