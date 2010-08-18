package com.firegnom.valkyrie.server.helpers;

import com.firegnom.valkyrie.server.player.MobPlayer;
import com.firegnom.valkyrie.server.player.User;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.NameNotBoundException;
import com.sun.sgs.app.ObjectNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerHelper.
 */
public class PlayerHelper {

	// map all users to a known namespace
	/** The Constant NAME_PREFIX. */
	private static final String NAME_PREFIX = "valkyrie:user:";

	/** The Constant MOB_NAME_PREFIX. */
	private static final String MOB_NAME_PREFIX = "valkyrie:mobPlayer:";

	/**
	 * Returns the player, or null if there is no player instance.
	 * 
	 * @param name
	 *            the name
	 * @return the user player
	 */
	public static User getUserPlayer(String name) {
		try {
			return (User) AppContext.getDataManager().getBinding(
					NAME_PREFIX + name);
		} catch (NameNotBoundException nnbe) {
			return null;
		} catch (ObjectNotFoundException onfe) {
			return null;
		}
	}

	/**
	 * Adds a player to the set of known players.
	 * 
	 * @param player
	 *            the player
	 */
	public static void addUserPlayer(User player) {
		String name = player.getName();
		if (getUserPlayer(name) != null) {
			throw new IllegalArgumentException("Player already exists: " + name);
		}
		AppContext.getDataManager().setBinding(NAME_PREFIX + name, player);
	}

	/**
	 * Gets the mob player.
	 * 
	 * @param name
	 *            the name
	 * @return the mob player
	 */
	public static MobPlayer getMobPlayer(String name) {
		try {
			return (MobPlayer) AppContext.getDataManager().getBinding(
					MOB_NAME_PREFIX + name);
		} catch (NameNotBoundException nnbe) {
			return null;
		} catch (ObjectNotFoundException onfe) {
			return null;
		}
	}

	/**
	 * Adds a player to the set of known players.
	 * 
	 * @param player
	 *            the player
	 */
	public static void addMobPlayer(MobPlayer player) {
		String name = player.getName();
		if (getMobPlayer(name) != null) {
			throw new IllegalArgumentException("Player already exists: " + name);
		}
		AppContext.getDataManager().setBinding(NAME_PREFIX + name, player);
	}

}
