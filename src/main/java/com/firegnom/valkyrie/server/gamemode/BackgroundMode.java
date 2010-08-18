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

import java.util.logging.Logger;

import com.firegnom.valkyrie.net.protocol.ChangeGameMode;
import com.firegnom.valkyrie.server.player.User;
import com.firegnom.valkyrie.share.constant.GameModes;

// TODO: Auto-generated Javadoc
/**
 * The Class BackgroundMode.
 */
public class BackgroundMode extends GameMode {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(BackgroundMode.class
			.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.gamemode.GameMode#getType()
	 */
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return GameModes.CREATE_PLAYER_MODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.server.gamemode.GameMode#join(com.firegnom.valkyrie
	 * .server.player.User)
	 */
	@Override
	public void join(User user) {
		super.join(user);
	}

	/**
	 * Listen for change to go back to game mode.
	 * 
	 * @param mode
	 *            the mode
	 */
	@Override
	public void received(ChangeGameMode mode) {
		if (mode.type == GameModes.MAP_MODE) {
			User u = user.get();
			u.changeMode(new MapMode());
		}
	}

}
