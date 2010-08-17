package com.firegnom.valkyrie.server.gamemode;

import java.util.logging.Logger;

import com.firegnom.valkyrie.net.protocol.ChangeGameMode;
import com.firegnom.valkyrie.server.player.User;
import com.firegnom.valkyrie.share.constant.GameModes;

public class BackgroundMode extends GameMode {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(BackgroundMode.class.getName());

  @Override
  public int getType() {
    // TODO Auto-generated method stub
    return GameModes.CREATE_PLAYER_MODE;
  }

  @Override
  public void join(User user) {
    super.join(user);
  }
  /**Listen for change to go back to game mode
   * 
   */
  @Override
  public void received(ChangeGameMode mode) {
    if (mode.type == GameModes.MAP_MODE){
      User u = user.get();
      u.changeMode(new MapMode());
    }
  }

}
