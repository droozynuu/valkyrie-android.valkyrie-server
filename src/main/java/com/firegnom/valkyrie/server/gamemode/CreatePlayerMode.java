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
