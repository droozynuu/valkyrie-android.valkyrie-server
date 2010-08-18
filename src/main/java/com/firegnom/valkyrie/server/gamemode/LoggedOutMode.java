/*
 * 
 */
package com.firegnom.valkyrie.server.gamemode;

import com.firegnom.valkyrie.share.constant.GameModes;

// TODO: Auto-generated Javadoc
/**
 * The Class LoggedOutMode.
 */
public class LoggedOutMode extends GameMode {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.gamemode.GameMode#getType()
	 */
	@Override
	public int getType() {
		return GameModes.LOGGED_OUT_MODE;
	}

}
