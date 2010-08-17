package com.firegnom.valkyrie.server.gamemode;

import java.util.logging.Logger;

import com.firegnom.valkyrie.net.protocol.ChangeGameMode;
import com.firegnom.valkyrie.net.protocol.CreateUserMessage;
import com.firegnom.valkyrie.net.protocol.helper.Protocol;
import com.firegnom.valkyrie.server.ValkyrieServer;
import com.firegnom.valkyrie.server.helpers.ZoneHelper;
import com.firegnom.valkyrie.server.player.User;
import com.firegnom.valkyrie.server.tasks.CreateNewPlayer;
import com.firegnom.valkyrie.share.constant.GameModes;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;


public class CreatePlayerMode extends GameMode{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(CreatePlayerMode.class.getName());
  
  @Override
  public int getType() {
    // TODO Auto-generated method stub
    return GameModes.CREATE_PLAYER_MODE;
  }
  @Override
  public void join(User user) {
    ChangeGameMode cgm = new ChangeGameMode();
    cgm.type = this.getType();
    user.send(Protocol.encode(cgm));
    super.join(user);
  }
  

  @Override
  public void received(CreateUserMessage customType) {
    User u = user.getForUpdate();
    u.setCreated(true);
    u.setZone(ZoneHelper.getZone(ValkyrieServer.INTRO_ZONE));
    u.playerClass = customType.playerClass;
    u.setPosition(new Point(9, 9));
    u.changeMode(new MapMode());
  }
  

}
