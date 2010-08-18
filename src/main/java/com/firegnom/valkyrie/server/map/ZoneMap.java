/*
 * 
 */
package com.firegnom.valkyrie.server.map;

import java.io.Serializable;

import com.firegnom.valkyrie.map.pathfinding.AStarPathFinder;
import com.firegnom.valkyrie.map.pathfinding.Mover;
import com.firegnom.valkyrie.map.pathfinding.Pathfindable;

// TODO: Auto-generated Javadoc
/**
 * The Class ZoneMap.
 */
public class ZoneMap implements Pathfindable, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The width. */
	public int width;

	/** The height. */
	public int height;

	/** The move matrix. */
	public short[][] moveMatrix;

	// public AStarPathFinder finder;

	/**
	 * Instantiates a new zone map.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public ZoneMap(int width, int height) {
		this.width = width;
		this.height = height;
		moveMatrix = new short[width][height];
		// finder = new AStarPathFinder(this, 20, 500, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.map.pathfinding.Pathfindable#getHeightInTiles()
	 */
	@Override
	public int getHeightInTiles() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.map.pathfinding.Pathfindable#getWidthInTiles()
	 */
	@Override
	public int getWidthInTiles() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.map.pathfinding.Pathfindable#pathFinderVisited(int,
	 * int)
	 */
	@Override
	public void pathFinderVisited(int x, int y) {
	}

	/**
	 * Gets the finder.
	 * 
	 * @return the finder
	 */
	public AStarPathFinder getFinder() {
		return new AStarPathFinder(this, 20, 500, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.map.pathfinding.Pathfindable#blocked(com.firegnom
	 * .valkyrie.map.pathfinding.Mover, int, int)
	 */
	@Override
	public boolean blocked(Mover mover, int x, int y) {
		if (x >= width || x < 0) {
			return true;
		}
		if (y >= height || y < 0) {
			return true;
		}
		return moveMatrix[x][y] != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.map.pathfinding.Pathfindable#getCost(com.firegnom
	 * .valkyrie.map.pathfinding.Mover, int, int, int, int)
	 */
	@Override
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		// TODO Auto-generated method stub
		return 1;
	}

}
