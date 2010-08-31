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

import java.util.HashSet;
import java.util.logging.Logger;

import com.firegnom.valkyrie.common.Constants;
import com.firegnom.valkyrie.net.protocol.PlayerDisconnected;
import com.firegnom.valkyrie.net.protocol.PlayerPositionMessage;
import com.firegnom.valkyrie.net.protocol.helper.Protocol;
import com.firegnom.valkyrie.server.player.MobPlayer;
import com.firegnom.valkyrie.server.player.Player;
import com.firegnom.valkyrie.share.constant.GameModes;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;

// TODO: Auto-generated Javadoc
/**
 * The Class MobMapMode.
 */
public class MobMapMode extends GameMode {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MobMapMode.class
			.getName());

	/** The player. */
	ManagedReference<MobPlayer> player;

	/**
	 * Join.
	 * 
	 * @param user
	 *            the user
	 */
	public void join(MobPlayer user) {
		player = AppContext.getDataManager().createReference(user);
		PlayerPositionMessage jppm = new PlayerPositionMessage();
		jppm.userName = user.getName();
		jppm.playerClass = user.getPlayerClass();
		Point pos  = user.getPosition().get().getPosition();
		jppm.x = (short) pos.getX();
		jppm.y = (short) pos.getY();
		HashSet<Player> inRange = user.getZone().getPlayersInRange(user,
				Constants.VISIBILITY_RANGE);
		for (Player u : inRange) {
			if (u.getMode().getType() == GameModes.MAP_MODE) {
				u.send(Protocol.encode(jppm));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.gamemode.GameMode#leave()
	 */
	@Override
	public void leave() {
		MobPlayer u = player.get();
		PlayerDisconnected pd = new PlayerDisconnected();
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
	 * @see com.firegnom.valkyrie.server.gamemode.GameMode#getType()
	 */
	@Override
	public int getType() {
		return GameModes.MAP_MODE;
	}

}
