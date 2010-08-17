package com.firegnom.valkyrie.server.gamemode;

import java.util.HashSet;
import java.util.logging.Logger;

import com.firegnom.valkyrie.common.Constants;
import com.firegnom.valkyrie.net.protocol.PlayerDisconnected;
import com.firegnom.valkyrie.net.protocol.PlayerPositionMessage;
import com.firegnom.valkyrie.net.protocol.helper.Protocol;
import com.firegnom.valkyrie.server.player.MobPlayer;
import com.firegnom.valkyrie.server.player.Player;
import com.firegnom.valkyrie.server.player.User;
import com.firegnom.valkyrie.share.constant.GameModes;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;

public class MobMapMode extends GameMode {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(MobMapMode.class.getName());
  
  ManagedReference<MobPlayer> player;

  public void join(MobPlayer user) {
    player = AppContext.getDataManager().createReference(user);
    PlayerPositionMessage  jppm = new PlayerPositionMessage();
    jppm.userName = user.getName();
    jppm.playerClass = user.getPlayerClass();
    jppm.x = (short)user.getPosition().getX();
    jppm.y = (short)user.getPosition().getY();
    HashSet<Player> inRange = user.getZone().getPlayersInRange(user, Constants.VISIBILITY_RANGE);
    for (Player u : inRange) {
      if (u.getMode().getType() == GameModes.MAP_MODE) {
        u.send(Protocol.encode(jppm));
      }
    }
  }

  @Override
  public void leave() {
    MobPlayer u = player.get();
    PlayerDisconnected pd = new PlayerDisconnected();
    pd.playerName = u.getName();
    HashSet<Player> inRange = u.getZone().getPlayersInRange(u, Constants.VISIBILITY_RANGE);
    for (Player user : inRange) {
      if (user.getMode().getType() == GameModes.MAP_MODE)
        user.send(Protocol.encode(pd));
    }

  }
  
  public int getType(){
    return GameModes.MAP_MODE;
  }


}
