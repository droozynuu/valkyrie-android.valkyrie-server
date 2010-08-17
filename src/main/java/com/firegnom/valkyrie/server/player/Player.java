package com.firegnom.valkyrie.server.player;

import java.nio.ByteBuffer;

import com.firegnom.valkyrie.server.gamemode.GameMode;
import com.firegnom.valkyrie.server.map.Zone;
import com.firegnom.valkyrie.util.Point;

public interface Player {
  public static final int LISTEN_MOVES=0; 
  public String getName() ;
  public Point getPosition();
  public void setPosition(Point p);
  public GameMode getMode();
  public int getPlayerClass();
  public Zone getZone();
  public void send(ByteBuffer bb);
  public boolean listen(int value);
  
  
}
