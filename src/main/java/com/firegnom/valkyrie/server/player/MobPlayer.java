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

public class MobPlayer  implements Player,ManagedObject, Serializable{

  private static final Logger logger = Logger.getLogger(MobPlayer.class.getName());
  private static final long serialVersionUID = 1L;
  private String name;
  
  //look to user
  private Point startPosition;
  private Point position;
  private int playerClass; 
  private int moveRange;
  
  private GameMode mode; 
  private ManagedReference <Zone> zone;


  public MobPlayer(String name) {
    this.name=name;
  }

  @Override
  public GameMode getMode() {
    return mode;
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return name;
  }

  @Override
  public Point getPosition() {
    // TODO Auto-generated method stub
    return position;
  }

  @Override
  public int getPlayerClass() {
    return playerClass;
  }

  @Override
  public Zone getZone() {
    return zone.get();
  }

  /**
   * @param position the position to set
   */
  
  public void setPosition(Point position) {
    this.position = position;
  }

  /**
   * @param zone the zone to set
   */
  public void setZone(Zone zone) {
    this.zone = AppContext.getDataManager().createReference(zone);
  }

  /**
   * @param mode the mode to set
   */
  public void setMode(GameMode mode) {
    this.mode = mode;
  }

  @Override
  public void send(ByteBuffer bb) {
    // TODO Auto-generated method stub
    
  }

  /**
   * @param playerClass the playerClass to set
   */
  public void setPlayerClass(int playerClass) {
    this.playerClass = playerClass;
  }

  public void startAI() {
    Random r = new Random();
    AppContext.getTaskManager().schedulePeriodicTask(new MoverTask(this,this.zone.get().getZoneMap()),5000+r.nextInt(10000),15000+r.nextInt(10000));
  }
  
  @Override
  public boolean listen(int flag){
    switch (flag){
      case LISTEN_MOVES:
        return false;
    }
    return false;
  }


  /**
   * @param startPosition the startPosition to set
   */
  public void setStartPosition(Point startPosition) {
    this.startPosition = startPosition;
    this.setPosition(startPosition);
  }

  /**
   * @return the startPosition
   */
  public Point getStartPosition() {
    return startPosition;
  }

  /**
   * @param moveRange the moveRange to set
   */
  public void setMoveRange(int moveRange) {
    this.moveRange = moveRange;
  }

  /**
   * @return the moveRange
   */
  public int getMoveRange() {
    return moveRange;
  }
  
}
