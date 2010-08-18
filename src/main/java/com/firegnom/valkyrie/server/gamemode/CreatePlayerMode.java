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
import com.firegnom.valkyrie.net.protocol.CreateUserMessage;
import com.firegnom.valkyrie.net.protocol.helper.Protocol;
import com.firegnom.valkyrie.server.ValkyrieServer;
import com.firegnom.valkyrie.server.helpers.ZoneHelper;
import com.firegnom.valkyrie.server.player.User;
import com.firegnom.valkyrie.share.constant.GameModes;
import com.firegnom.valkyrie.util.Point;

// TODO: Auto-generated Javadoc
/**
 * The Class CreatePlayerMode.
 */
public class CreatePlayerMode extends GameMode {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(CreatePlayerMode.class.getName());

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
		ChangeGameMode cgm = new ChangeGameMode();
		cgm.type = this.getType();
		user.send(Protocol.encode(cgm));
		super.join(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.firegnom.valkyrie.server.gamemode.GameMode#received(com.firegnom.
	 * valkyrie.net.protocol.CreateUserMessage)
	 */
	@Override
	public void received(CreateUserMessage customType) {
		User u = user.getForUpdate();
		u.setCreated(true);
		u.setZone(ZoneHelper.getZone(ValkyrieServer.INTRO_ZONE));
		u.playerClass = customType.playerClass;
		u.setPosition(new Point(9, 9));
		u.changeMode(new MapMode());
	}

}
