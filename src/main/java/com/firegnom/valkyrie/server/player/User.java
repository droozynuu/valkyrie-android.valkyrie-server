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

public class User implements ClientSessionListener,ManagedObject, Serializable, Player{
  private static final Logger logger = Logger.getLogger(User.class.getName());
  private static final long serialVersionUID = 1L;
  private String username;

  //  put position in to the different class and only keep reference
  private Point position;
  public int playerClass; 
  
  private GameMode mode;
  private ManagedReference<? extends ClientSession> sessionRef;
  private ManagedReference <Zone> zone;
  private boolean created = false;
  
  
  public User(String username) {
    logger.log(Level.INFO,"User : {0} created:",username);
    this.username = username;
  }
  public void setSession(ClientSession session){
    //check if u are logged in already;
    sessionRef = AppContext.getDataManager().createReference(session);
  }
  
  
  public void changeMode(GameMode m){
    logger.log(Level.INFO,"chamgeing game mode to :{0}",m.getType());
    if (mode != null){
      mode.leave();
    }
    this.mode = m;
    m.join(this);
  }



  @Override
  public void disconnected(boolean arg0) {
      changeMode(new LoggedOutMode());
  }

  @Override
  public void receivedMessage(ByteBuffer arg0) {
   logger.log(Level.INFO,"User : {0} recieaved message:",username);
   Protocol protocol = new Protocol(mode);
   protocol.decode(arg0);
  }
  
 
  @Override
  public Point getPosition() {
    return position; 
  }
  
  public void setPosition(Point position) {
    this.position = position;
  }
  @Override
  public void send(ByteBuffer bb){
    ClientSession clientSession;
    try{
     clientSession = sessionRef.get();
    }catch (ObjectNotFoundException e) {
      logger.log(Level.INFO,"My mode should be 2 and is :{0}",mode.getType());
      return;
    }
    clientSession.send(bb);
  }




  @Override
  public String getName() {
    return username;
  }


  public boolean isCreated() {
    return created ;
  }


  @Override
  public GameMode getMode() {
    return mode;
  }
  
  public void setCreated(boolean b) {
    created = b;
  }
  
  public void setZone(Zone zone) {
    if (this.zone!=null) this.zone.get().leave(this); 
    this.zone = AppContext.getDataManager().createReference(zone);
    zone.join(this);
  }
  public Zone getZone() {
    return zone.get();
  }
  @Override
  public int getPlayerClass() {
    return playerClass;
  }
  public boolean listen(int flag){
    switch (flag){
      case LISTEN_MOVES:
        return true;
    }
    return false;
  }


  
 
  
  

}
