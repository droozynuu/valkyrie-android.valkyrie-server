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
