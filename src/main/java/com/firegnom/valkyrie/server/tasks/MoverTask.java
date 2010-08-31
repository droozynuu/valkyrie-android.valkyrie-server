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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;

import com.firegnom.valkyrie.map.pathfinding.Mover;
import com.firegnom.valkyrie.server.ValkyrieServer;
import com.firegnom.valkyrie.server.map.ZoneMap;
import com.firegnom.valkyrie.server.player.MobPlayer;
import com.firegnom.valkyrie.server.player.PlayerPosition;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.Task;

// TODO: Auto-generated Javadoc
/**
 * The Class MoverTask.
 */
public class MoverTask implements Task, Mover, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(MoverTask.class
			.getName());

	/** The player. */
	ManagedReference<MobPlayer> player;

	/** The zone map. */
	ZoneMap zoneMap;

	/**
	 * Instantiates a new mover task.
	 * 
	 * @param player
	 *            the player
	 * @param zonemap
	 *            the zonemap
	 */
	public MoverTask(MobPlayer player, ZoneMap zonemap) {
		this.player = AppContext.getDataManager().createReference(player);
		this.zoneMap = zonemap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.sgs.app.Task#run()
	 */
	@Override
	public void run() throws Exception {
		Random r = new Random();
		MobPlayer p = player.get();
		PlayerPosition pp = p.getPosition().getForUpdate();
		Point po = pp.getPosition().clone();
		Point po1 = null;

		// TODO limit try times !!!!
		while (po1 == null) {
			po1 = new Point(po.coordinates[0] + (r.nextInt(16) - 8),
					po.coordinates[1] + (r.nextInt(16) - 8));
			if (zoneMap.blocked(null, po1.getX(), po1.getY())) {
				po1 = null;
				continue;
			}
			if (!po1.inRange(po, p.getMoveRange())) {
				po1 = null;
				continue;
			}
		}
		pp.setPosition(po1);
		AppContext.getTaskManager().scheduleTask(
				new BroadcastMoveTask(p, zoneMap, po,po1));
		// Random r = new Random();
		// AppContext.getTaskManager().scheduleTask(new
		// MoverTask(p,zoneMap),r.nextInt(10000));
	}

}
