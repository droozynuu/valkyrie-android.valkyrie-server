/*
 * 
 */
package com.firegnom.valkyrie.server.tasks;

import java.io.Serializable;

import com.firegnom.valkyrie.server.ValkyrieServer;
import com.firegnom.valkyrie.server.gamemode.MapMode;
import com.firegnom.valkyrie.server.helpers.ZoneHelper;
import com.firegnom.valkyrie.server.player.User;
import com.firegnom.valkyrie.util.Point;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.Task;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateNewPlayer.
 */
public class CreateNewPlayer implements Task, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user. */
	ManagedReference<User> user;

	/** The playerclass. */
	int playerclass;

	/**
	 * Instantiates a new creates the new player.
	 * 
	 * @param u
	 *            the u
	 * @param playerClass
	 *            the player class
	 */
	public CreateNewPlayer(User u, int playerClass) {
		user = AppContext.getDataManager().createReference(u);
		this.playerclass = playerClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.sgs.app.Task#run()
	 */
	@Override
	public void run() throws Exception {
		execute();
	}

	/**
	 * Execute.
	 */
	public void execute() {
		User u = user.getForUpdate();
		u.setCreated(true);
		u.setZone(ZoneHelper.getZone(ValkyrieServer.INTRO_ZONE));
		u.playerClass = playerclass;
		u.setPosition(new Point(9, 9));
		u.changeMode(new MapMode());
	}
}
