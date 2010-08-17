package com.firegnom.valkyrie.server.tasks;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

import com.firegnom.valkyrie.common.Constants;
import com.firegnom.valkyrie.map.pathfinding.AStarPathFinder;
import com.firegnom.valkyrie.map.pathfinding.Mover;
import com.firegnom.valkyrie.net.protocol.Path;
import com.firegnom.valkyrie.net.protocol.PlayerMove;
import com.firegnom.valkyrie.net.protocol.helper.Protocol;
import com.firegnom.valkyrie.server.map.ZoneMap;
import com.firegnom.valkyrie.server.player.MobPlayer;
import com.firegnom.valkyrie.server.player.Player;
import com.firegnom.valkyrie.share.constant.GameModes;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.Task;

public class MoverTask implements Task,Mover,Serializable{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  ManagedReference<MobPlayer> player;
  ZoneMap zoneMap;
  public MoverTask(MobPlayer player, ZoneMap zonemap) {
    this.player = AppContext.getDataManager().createReference(player);
    zoneMap = zonemap; 
  }
  @Override
  public void run() throws Exception {
    Random r = new Random();
    MobPlayer p = player.getForUpdate();
    Point po = p.getPosition();
    Point po1 = null;
    
    //TODO limit try times !!!!
    while (po1==null){
      po1 = new Point(po.coordinates[0]+(r.nextInt(16)-8),po.coordinates[1]+(r.nextInt(16)-8)) ;
      if (zoneMap.blocked(null, po1.getX(), po1.getY())) 
        {
          po1= null;
          continue;
        }
      if (!po1.inRange(p.getStartPosition(), p.getMoveRange())){
        po1= null;
        continue;
      }
    }
    p.setPosition(po1);
    AppContext.getTaskManager().scheduleTask(new BroadcastMoveTask(p, zoneMap, po));
//    Random r = new Random();
//    AppContext.getTaskManager().scheduleTask(new MoverTask(p,zoneMap),r.nextInt(10000));
  }
  
}