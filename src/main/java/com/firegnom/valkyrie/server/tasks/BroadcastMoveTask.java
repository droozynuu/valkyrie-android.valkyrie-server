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
package com.firegnom.valkyrie.server.tasks;

import java.io.Serializable;
import java.util.HashSet;

import com.firegnom.valkyrie.common.Constants;
import com.firegnom.valkyrie.map.pathfinding.AStarPathFinder;
import com.firegnom.valkyrie.map.pathfinding.Mover;
import com.firegnom.valkyrie.net.protocol.Path;
import com.firegnom.valkyrie.net.protocol.PlayerMove;
import com.firegnom.valkyrie.net.protocol.helper.Protocol;
import com.firegnom.valkyrie.server.map.ZoneMap;
import com.firegnom.valkyrie.server.player.MobPlayer;
import com.firegnom.valkyrie.server.player.Player;
import com.firegnom.valkyrie.share.constant.GameModes;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.Task;

// TODO: Auto-generated Javadoc
/**
 * The Class BroadcastMoveTask.
 */
public class BroadcastMoveTask implements Task, Mover, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The player. */
	ManagedReference<MobPlayer> player;

	/** The zone map. */
	ZoneMap zoneMap;

	/** The last position. */
	Point lastPosition;

	/**
	 * Instantiates a new broadcast move task.
	 * 
	 * @param player
	 *            the player
	 * @param zonemap
	 *            the zonemap
	 * @param lastposition
	 *            the lastposition
	 */
	public BroadcastMoveTask(MobPlayer player, ZoneMap zonemap,
			Point lastposition) {
		this.player = AppContext.getDataManager().createReference(player);
		lastPosition = lastposition;
		zoneMap = zonemap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.sgs.app.Task#run()
	 */
	@Override
	public void run() throws Exception {
		MobPlayer p = player.get();
		Point po = p.getPosition();
		AStarPathFinder finder = zoneMap.getFinder();
		if (finder == null) {
			throw new RuntimeException("null finder ");
		}
		com.firegnom.valkyrie.map.pathfinding.Path findPath = finder.findPath(
				null, lastPosition.getX(), lastPosition.getY(), po.getX(),
				po.getY());
		if (findPath == null) {
			return;
		}
		Path path = findPath.convertToNetPath();
		HashSet<Player> inRange = p.getZone().getPlayersInRange(p,
				lastPosition, Constants.VISIBILITY_RANGE, GameModes.MAP_MODE);
		PlayerMove pm = new PlayerMove();
		pm.playerName = p.getName();
		pm.playerClass = p.getPlayerClass();
		pm.path = path;
		for (Player user : inRange) {
			user.send(Protocol.encode(pm));
		}

	}

}
