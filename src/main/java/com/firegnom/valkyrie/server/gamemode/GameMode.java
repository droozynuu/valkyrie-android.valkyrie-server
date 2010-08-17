package com.firegnom.valkyrie.server.gamemode;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.firegnom.valkyrie.net.protocol.ChangeGameMode;
import com.firegnom.valkyrie.net.protocol.ChatMessage;
import com.firegnom.valkyrie.net.protocol.ChatUserJoined;
import com.firegnom.valkyrie.net.protocol.ChatUserLeft;
import com.firegnom.valkyrie.net.protocol.ConfirmMove;
import com.firegnom.valkyrie.net.protocol.CreateUserMessage;
import com.firegnom.valkyrie.net.protocol.PlayerDisconnected;
import com.firegnom.valkyrie.net.protocol.PlayerInfoMessage;
import com.firegnom.valkyrie.net.protocol.PlayerMove;
import com.firegnom.valkyrie.net.protocol.PlayerPositionMessage;
import com.firegnom.valkyrie.net.protocol.PlayerPositionsMessage;
import com.firegnom.valkyrie.net.protocol.RequestPlayerInfoMessage;
import com.firegnom.valkyrie.net.protocol.RequestPlayersPositionMessage;
import com.firegnom.valkyrie.net.protocol.helper.MessageListener;
import com.firegnom.valkyrie.server.player.User;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;

public  class GameMode implements MessageListener ,Serializable{
  private static final Logger logger = Logger.getLogger(GameMode.class.getName());
  private static final long serialVersionUID = 1L;
  
  
  ManagedReference<User> user ;
  
  public int getType(){
    return 0;
  }
  
  public void join(User user) {
    this.user = AppContext.getDataManager().createReference(user);
  }
  public  void leave(){
    this.user = null;
  }

  @Override
  public void received(PlayerMove customType) {
    logger.log(Level.INFO,"received:PlayerMove");
    
  }

  @Override
  public void received(ConfirmMove customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(PlayerDisconnected customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(ChatMessage customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(ChatUserJoined customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(ChatUserLeft customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(CreateUserMessage customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(ChangeGameMode customType) {
    // TODO Auto-generated method stub
    
  }


  @Override
  public void received(PlayerPositionMessage customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(RequestPlayerInfoMessage customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(PlayerInfoMessage customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(PlayerPositionsMessage customType) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void received(RequestPlayersPositionMessage customType) {
    // TODO Auto-generated method stub
    
  }
  

}
