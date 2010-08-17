package com.firegnom.valkyrie.server.map;

import java.io.Serializable;

import com.firegnom.valkyrie.map.pathfinding.AStarPathFinder;
import com.firegnom.valkyrie.map.pathfinding.Mover;
import com.firegnom.valkyrie.map.pathfinding.Pathfindable;


public class ZoneMap implements Pathfindable, Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public int width;
  public int height;
  
  public short[][] moveMatrix;
//  public AStarPathFinder finder;
  
  public ZoneMap(int width, int height) {
    this.width = width;
    this.height = height;
    moveMatrix = new short[width][height];
//    finder = new AStarPathFinder(this, 20, 500, true);
  }

  
  @Override
  public int getHeightInTiles() {
    return height ;
  }

  @Override
  public int getWidthInTiles() {
    return width;
  }

  @Override
  public void pathFinderVisited(int x, int y) {
  }
  
  public AStarPathFinder getFinder(){
    return new AStarPathFinder(this, 20, 500, true);
  }

  @Override
  public boolean blocked(Mover mover, int x, int y) {
    if (x>=width||x <0) return true;
    if (y>=height||y <0) return true;
    return moveMatrix[x][y] != 0;
  }

  @Override
  public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
    // TODO Auto-generated method stub
    return 1;
  }

 

}
