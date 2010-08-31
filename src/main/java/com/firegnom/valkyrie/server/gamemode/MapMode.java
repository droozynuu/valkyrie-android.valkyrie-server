/*******************************************************************************
 * Copyright (c) 2010 Maciej Kaniewski (mk@firegnom.com).
 *  
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 3 of the License, or
 *     (at your option) any later version.
 *  
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *  
 *     You should have received a copy of the GNU General Public License
 *     along with this program; if not, write to the Free Software Foundation,
 *     Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 *  
 *     Contributors:
 *      Maciej Kaniewski (mk@firegnom.com) - initial API and implementation
 ******************************************************************************/
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
		Point pos  = user.getPosition().get().getPosition();
		jppm.userName = user.getName();
		jppm.playerClass = user.playerClass;
		jppm.x = (short) pos.getX();
		jppm.y = (short) pos.getY();
		HashSet<Player> inRange = user.getZone().getPlayersInRange(user,
				Constants.VISIBILITY_RANGE);
		ArrayList<PlayerPositionMessage> ppms = new ArrayList<PlayerPositionMessage>();
		for (Player u : inRange) {
			if (u.getMode().getType() == GameModes.MAP_MODE) {
				u.send(Protocol.encode(jppm));
				PlayerPositionMessage ppm = new PlayerPositionMessage();
				ppm.playerClass = u.getPlayerClass();
				Point pos1  = u.getPosition().get().getPosition();
				ppm.x = (short) pos1.getX();
				ppm.y = (short) pos1.getY();
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
		User u = user.get();
		HashSet<Player> inRange = u.getZone().getPlayersInRange(u,
				Constants.VISIBILITY_RANGE);
		u.getPosition().getForUpdate().setPosition(p);
		inRange = u.getZone().getPlayersInRange(u, Constants.VISIBILITY_RANGE,
				inRange);
		if (inRange.size() <= 0) {
			return;
		}
		
		//new task 
		ArrayList<PlayerPositionMessage> ppms = new ArrayList<PlayerPositionMessage>();
		for (Player user : inRange) {
			if (user.getMode().getType() == GameModes.MAP_MODE) {
				user.send(Protocol.encode(customType));
				PlayerPositionMessage ppm = new PlayerPositionMessage();
				ppm.playerClass = user.getPlayerClass();
				Point pos  = user.getPosition().get().getPosition();
				ppm.x = (short) pos.getX();
				ppm.y = (short) pos.getY();
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
				Point pos  = u.getPosition().get().getPosition();
				ppm.x = (short) pos.getX();
				ppm.y = (short) pos.getY();
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
		Point pos  = u.getPosition().get().getPosition();
		msg.x = (short) pos.getX();
		msg.y = (short) pos.getY();
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
