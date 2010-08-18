/*
 * 
 */
package com.firegnom.valkyrie.server.gamemode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.firegnom.valkyrie.common.Constants;
import com.firegnom.valkyrie.net.protocol.ChangeGameMode;
import com.firegnom.valkyrie.net.protocol.PlayerDisconnected;
import com.firegnom.valkyrie.net.protocol.PlayerInfoMessage;
import com.firegnom.valkyrie.net.protocol.PlayerMove;
import com.firegnom.valkyrie.net.protocol.PlayerPositionMessage;
import com.firegnom.valkyrie.net.protocol.PlayerPositionsMessage;
import com.firegnom.valkyrie.net.protocol.RequestPlayerInfoMessage;
import com.firegnom.valkyrie.net.protocol.RequestPlayersPositionMessage;
import com.firegnom.valkyrie.net.protocol.Step;
import com.firegnom.valkyrie.net.protocol.helper.Protocol;
import com.firegnom.valkyrie.server.player.Player;
import com.firegnom.valkyrie.server.player.User;
import com.firegnom.valkyrie.share.constant.GameModes;
import com.firegnom.valkyrie.util.Point;

// TODO: Auto-generated Javadoc
/**
 * The Class MapMode.
 */
public class MapMode extends GameMode {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MapMode.class
			.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.server.gamemode.GameMode#join(com.firegnom.valkyrie
	 * .server.player.User)
	 */
	@Override
	public void join(User user) {

		ChangeGameMode cgm = new ChangeGameMode();
		cgm.type = this.getType();
		user.send(Protocol.encode(cgm));
		PlayerPositionMessage jppm = new PlayerPositionMessage();
		jppm.userName = user.getName();
		jppm.playerClass = user.playerClass;
		jppm.x = (short) user.getPosition().getX();
		jppm.y = (short) user.getPosition().getY();
		HashSet<Player> inRange = user.getZone().getPlayersInRange(user,
				Constants.VISIBILITY_RANGE);
		ArrayList<PlayerPositionMessage> ppms = new ArrayList<PlayerPositionMessage>();
		for (Player u : inRange) {
			if (u.getMode().getType() == GameModes.MAP_MODE) {
				u.send(Protocol.encode(jppm));
				PlayerPositionMessage ppm = new PlayerPositionMessage();
				ppm.playerClass = u.getPlayerClass();
				ppm.x = (short) u.getPosition().getX();
				ppm.y = (short) u.getPosition().getY();
				ppm.userName = u.getName();
				ppms.add(ppm);
			}

		}
		PlayerPositionsMessage ppsm = new PlayerPositionsMessage();
		ppsm.playerPositionMessage = ppms
				.toArray(new PlayerPositionMessage[ppms.size()]);
		user.send(Protocol.encode(ppsm));
		super.join(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.gamemode.GameMode#leave()
	 */
	@Override
	public void leave() {
		PlayerDisconnected pd = new PlayerDisconnected();
		User u = user.get();
		pd.playerName = u.getName();
		HashSet<Player> inRange = u.getZone().getPlayersInRange(u,
				Constants.VISIBILITY_RANGE);

		for (Player user : inRange) {
			if (user.getMode().getType() == GameModes.MAP_MODE) {
				user.send(Protocol.encode(pd));
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.server.gamemode.GameMode#received(com.firegnom.
	 * valkyrie.net.protocol.PlayerMove)
	 */
	@Override
	public void received(PlayerMove customType) {
		logger.log(Level.FINEST, "received:PlayerMove");
		Step[] s = customType.path.step;
		Point p = new Point(s[s.length - 1].x, s[s.length - 1].y);
		logger.log(Level.FINE, "received:PlayerMoveposition {0}", p);
		// change this code to a new function which takes 2 parameters start and
		// stop
		User u = user.getForUpdate();
		HashSet<Player> inRange = u.getZone().getPlayersInRange(u,
				Constants.VISIBILITY_RANGE);
		u.setPosition(p);
		inRange = u.getZone().getPlayersInRange(u, Constants.VISIBILITY_RANGE,
				inRange);
		if (inRange.size() <= 0) {
			return;
		}
		ArrayList<PlayerPositionMessage> ppms = new ArrayList<PlayerPositionMessage>();
		for (Player user : inRange) {
			if (user.getMode().getType() == GameModes.MAP_MODE) {
				user.send(Protocol.encode(customType));
				PlayerPositionMessage ppm = new PlayerPositionMessage();
				ppm.playerClass = user.getPlayerClass();
				ppm.x = (short) user.getPosition().getX();
				ppm.y = (short) user.getPosition().getY();
				ppm.userName = user.getName();
				ppms.add(ppm);
			}

		}
		PlayerPositionsMessage ppsm = new PlayerPositionsMessage();
		ppsm.playerPositionMessage = ppms
				.toArray(new PlayerPositionMessage[ppms.size()]);
		;

		u.send(Protocol.encode(ppsm));
		//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.server.gamemode.GameMode#received(com.firegnom.
	 * valkyrie.net.protocol.RequestPlayersPositionMessage)
	 */
	@Override
	public void received(RequestPlayersPositionMessage customType) {
		User user = this.user.get();
		HashSet<Player> inRange = user.getZone().getPlayersInRange(user,
				Constants.VISIBILITY_RANGE);
		ArrayList<PlayerPositionMessage> ppms = new ArrayList<PlayerPositionMessage>();
		for (Player u : inRange) {
			if (u.getMode().getType() == GameModes.MAP_MODE) {
				PlayerPositionMessage ppm = new PlayerPositionMessage();
				ppm.playerClass = u.getPlayerClass();
				ppm.x = (short) u.getPosition().getX();
				ppm.y = (short) u.getPosition().getY();
				ppm.userName = u.getName();
				ppms.add(ppm);
			}
		}
		PlayerPositionsMessage ppsm = new PlayerPositionsMessage();
		ppsm.playerPositionMessage = ppms
				.toArray(new PlayerPositionMessage[ppms.size()]);
		user.send(Protocol.encode(ppsm));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.gamemode.GameMode#getType()
	 */
	@Override
	public int getType() {
		return GameModes.MAP_MODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.server.gamemode.GameMode#received(com.firegnom.
	 * valkyrie.net.protocol.RequestPlayerInfoMessage)
	 */
	@Override
	public void received(RequestPlayerInfoMessage customType) {
		PlayerPositionMessage msg = new PlayerPositionMessage();
		User u = user.get();
		msg.userName = u.getName();
		msg.playerClass = u.playerClass;
		msg.x = (short) u.getPosition().getX();
		msg.y = (short) u.getPosition().getY();
		PlayerInfoMessage m = new PlayerInfoMessage();
		m.zoneName = u.getZone().getName();
		m.position = msg;
		m.playerClass = u.playerClass;
		user.get().send(Protocol.encode(m));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.server.gamemode.GameMode#received(com.firegnom.
	 * valkyrie.net.protocol.ChangeGameMode)
	 */
	@Override
	public void received(ChangeGameMode mode) {
		if (mode.type == GameModes.BACKGROUND) {
			User u = user.get();
			u.changeMode(new BackgroundMode());
		}
	}

}
