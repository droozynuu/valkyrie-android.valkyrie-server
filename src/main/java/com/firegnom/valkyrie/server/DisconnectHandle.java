/*
 * 
 */
package com.firegnom.valkyrie.server;

import com.firegnom.valkyrie.server.player.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface DisconnectHandle.
 */
public interface DisconnectHandle {

	/**
	 * Disconnected.
	 * 
	 * @param user
	 *            the user
	 */
	void disconnected(User user);
}
