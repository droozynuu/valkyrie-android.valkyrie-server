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
