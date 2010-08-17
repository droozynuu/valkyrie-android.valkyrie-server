package com.firegnom.valkyrie.server.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.firegnom.valkyrie.server.player.Player;
import com.firegnom.valkyrie.server.player.User;
import com.firegnom.valkyrie.share.constant.GameModes;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;

public class Zone implements Serializable,ManagedObject{
  private ZoneMap zoneMap;
  private String name;
  int shoutRange = 50;
  private List <ManagedReference<Player>> users;
  private static final long serialVersionUID = 1L;
  public Zone(String name) {
    this.name = name;
    users = new ArrayList<ManagedReference<Player>>();
  }
  
  public void join(Player user){
    users.add(AppContext.getDataManager().createReference(user));
  }
  public void leave(Player user){
    users.remove(AppContext.getDataManager().createReference(user));
  }
  public String getName(){
    return name;
    
  }
  public HashSet <Player> getPlayersInRange(Player user,Point goTo ,int range,int mapMode){
    HashSet<Player> ret = new HashSet<Player>();
    Point start = user.getPosition();
    for ( ManagedReference<Player>  u : users) {
      Player us = u.get();
      if (us.listen(Player.LISTEN_MOVES)
          &&(!us.getName().equals(user.getName()))
          &&(us.getMode().getType() == mapMode)
          &&(us.getPosition().inRange(start, range) || us.getPosition().inRange(goTo, range)))
        ret.add(us);
    }
    return ret;
  }
  
  public HashSet <Player> getPlayersInRange(Player user ,int range){
    return getPlayersInRange(user, range, null);
  }
  
  public HashSet <Player> getPlayersInRange(Player user ,int range,HashSet <Player> append){
    Point p = user.getPosition();
    HashSet<Player> ret ;
    if (append ==null)
      ret = new HashSet<Player>();
    else
      ret =append;
    for ( ManagedReference<Player>  u : users) {
      Player us = u.get();
      if (us.getPosition().inRange(p, range)&&!us.getName().equals(user.getName()))
        ret.add(us);
    }
    return ret;
  }

  /**
   * @param zoneMap the zoneMap to set
   */
  public void setZoneMap(ZoneMap zoneMap) {
    this.zoneMap = zoneMap;
  }

  /**
   * @return the zoneMap
   */
  public ZoneMap getZoneMap() {
    return zoneMap;
  }
  
}
