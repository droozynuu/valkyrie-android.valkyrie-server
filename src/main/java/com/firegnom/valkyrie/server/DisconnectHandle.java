package com.firegnom.valkyrie.server;

import com.firegnom.valkyrie.server.player.User;

public interface DisconnectHandle {
  void disconnected(User user);
}
