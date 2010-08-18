/*
 * 
 */
package com.firegnom.valkyrie.server.tasks;

import java.io.Serializable;
import java.util.Random;

import com.firegnom.valkyrie.map.pathfinding.Mover;
import com.firegnom.valkyrie.server.map.ZoneMap;
import com.firegnom.valkyrie.server.player.MobPlayer;
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
		zoneMap = zonemap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.sgs.app.Task#run()
	 */
	@Override
	public void run() throws Exception {
		Random r = new Random();
		MobPlayer p = player.getForUpdate();
		Point po = p.getPosition();
		Point po1 = null;

		// TODO limit try times !!!!
		while (po1 == null) {
			po1 = new Point(po.coordinates[0] + (r.nextInt(16) - 8),
					po.coordinates[1] + (r.nextInt(16) - 8));
			if (zoneMap.blocked(null, po1.getX(), po1.getY())) {
				po1 = null;
				continue;
			}
			if (!po1.inRange(p.getStartPosition(), p.getMoveRange())) {
				po1 = null;
				continue;
			}
		}
		p.setPosition(po1);
		AppContext.getTaskManager().scheduleTask(
				new BroadcastMoveTask(p, zoneMap, po));
		// Random r = new Random();
		// AppContext.getTaskManager().scheduleTask(new
		// MoverTask(p,zoneMap),r.nextInt(10000));
	}

}