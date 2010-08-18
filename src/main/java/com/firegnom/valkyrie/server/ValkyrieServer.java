/*
 * 
 */
package com.firegnom.valkyrie.server;

import java.io.Serializable;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.firegnom.valkyrie.server.gamemode.CreatePlayerMode;
import com.firegnom.valkyrie.server.gamemode.MapMode;
import com.firegnom.valkyrie.server.gamemode.MobMapMode;
import com.firegnom.valkyrie.server.helpers.PlayerHelper;
import com.firegnom.valkyrie.server.helpers.ZoneHelper;
import com.firegnom.valkyrie.server.map.Zone;
import com.firegnom.valkyrie.server.map.ZoneMap;
import com.firegnom.valkyrie.server.player.MobPlayer;
import com.firegnom.valkyrie.server.player.User;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;

// TODO: Auto-generated Javadoc
/**
 * The Class ValkyrieServer.
 */
public class ValkyrieServer implements AppListener, Serializable,
		DisconnectHandle {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ValkyrieServer.class
			.getName());

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant INTRO_ZONE. */
	public static final String INTRO_ZONE = "introv9";

	/** The online users. */
	int onlineUsers = 0;

	/** The mm. */
	MapMode mm;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.sgs.app.AppListener#initialize(java.util.Properties)
	 */
	public void initialize(Properties props) {

		Zone intro = new Zone(INTRO_ZONE);
		intro.setZoneMap(new ZoneMap(80, 80));
		ZoneHelper.addZone(intro);

		Random r = new Random();
		for (int i = 0; i < 20; i++) {
			int x = r.nextInt(80);
			int y = r.nextInt(80);
			MobPlayer rabbit = new MobPlayer("MobPlayer-" + i);
			rabbit.setStartPosition(new Point(x, y));
			rabbit.setPlayerClass(5 + r.nextInt(5));
			rabbit.setZone(intro);
			rabbit.setMoveRange(5);
			MobMapMode mmm = new MobMapMode();
			rabbit.setMode(mmm);
			intro.join(rabbit);
			mmm.join(rabbit);
			PlayerHelper.addMobPlayer(rabbit);
			rabbit.startAI();
		}

		// for (int j = 0 ; j < 100;j++){
		// Zone zone = new Zone("intro_"+j);
		// zone.setZoneMap(new ZoneMap(80, 80));
		// ZoneHelper.addZone(zone);
		//
		// for (int i = 0 ; i < 10;i++){
		// int x= r.nextInt(80);
		// int y = r.nextInt(80);
		// MobPlayer rabbit = new MobPlayer("MobPlayer-"+x+","+y);
		// rabbit.setPosition(new Point(x, y));
		// rabbit.setPlayerClass(5+r.nextInt(4));
		// rabbit.setZone(zone);
		// MobMapMode mmm= new MobMapMode();
		// rabbit.setMode(mmm);
		// zone.join(rabbit);
		// mmm.join(rabbit);
		// PlayerHelper.addMobPlayer(rabbit);
		// rabbit.startAI();
		// }
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.sgs.app.AppListener#loggedIn(com.sun.sgs.app.ClientSession)
	 */
	public ClientSessionListener loggedIn(ClientSession session) {
		logger.log(Level.INFO, "User {0} has logged in", session.getName());
		// User macio = UserHelper.getUserPlayer("macio");
		// if (macio!= null&&macio.getMode() != null)
		// logger.log(Level.INFO, "macio mode : {0} ",
		// macio.getMode().getType());
		User u = getUserPlayer(session.getName());

		u.setSession(session);
		if (u.isCreated()) {
			u.changeMode(new MapMode());
		} else {
			u.changeMode(new CreatePlayerMode());
		}
		return u;
		// return null;
	}

	/**
	 * Gets the user player.
	 * 
	 * @param name
	 *            the name
	 * @return the user player
	 */
	public User getUserPlayer(String name) {
		User player = PlayerHelper.getUserPlayer(name);
		if (player == null) {
			player = new User(name);
			PlayerHelper.addUserPlayer(player);
		}

		onlineUsers++;
		return player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.server.DisconnectHandle#disconnected(com.firegnom
	 * .valkyrie.server.player.User)
	 */
	@Override
	public void disconnected(User user) {
		// AppContext.getDataManager().markForUpdate(user);
		// user.changeMode(new LoggedOutMode());
		// logger.log(Level.INFO, "User {0} disconnected", user.getName());
		onlineUsers--;
	}

}
