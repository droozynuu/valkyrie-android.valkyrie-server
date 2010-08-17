package com.firegnom.valkyrie.server.helpers;

import com.firegnom.valkyrie.server.map.Zone;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.NameNotBoundException;
import com.sun.sgs.app.ObjectNotFoundException;

public class ZoneHelper {

//map all users to a known namespace
  private static final String NAME_PREFIX = "valkyrie:zone:";
  
  /** Returns the player, or null if there is no player instance. */
  public static Zone getZone(String name) {
      try {
          return (Zone) AppContext.getDataManager().
              getBinding(NAME_PREFIX + name);
      } catch (NameNotBoundException nnbe) {
          return null;
      } catch (ObjectNotFoundException onfe) {
          return null;
      }
  }

  /** Adds a player to the set of known players. */
  public static void addZone(Zone zone) {
      String name = zone.getName();
      if (getZone(name) != null) {
          throw new IllegalArgumentException("Player already exists: " +
                                             name);
      }
      AppContext.getDataManager().setBinding(NAME_PREFIX + name, zone);
  }
  
}
