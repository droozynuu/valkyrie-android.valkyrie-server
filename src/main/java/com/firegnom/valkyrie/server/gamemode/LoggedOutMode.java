package com.firegnom.valkyrie.server.gamemode;

import com.firegnom.valkyrie.share.constant.GameModes;

public class LoggedOutMode extends GameMode{

  private static final long serialVersionUID = 1L;
  
  @Override
  public
  int getType() {
    return GameModes.LOGGED_OUT_MODE;
  }

}
