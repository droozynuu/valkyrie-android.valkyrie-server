/*
 * 
 */
package com.firegnom.valkyrie.server.gamemode;

import java.util.HashSet;
import java.util.logging.Logger;

import com.firegnom.valkyrie.common.Constants;
import com.firegnom.valkyrie.net.protocol.PlayerDisconnected;
import com.firegnom.valkyrie.net.protocol.PlayerPositionMessage;
import com.firegnom.valkyrie.net.protocol.helper.Protocol;
import com.firegnom.valkyrie.server.player.MobPlayer;
import com.firegnom.valkyrie.server.player.Player;
import com.firegnom.valkyrie.share.constant.GameModes;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;

// TODO: Auto-generated Javadoc
/**
 * The Class MobMapMode.
 */
public class MobMapMode extends GameMode {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MobMapMode.class
			.getName());

	/** The player. */
	ManagedReference<MobPlayer> player;

	/**
	 * Join.
	 * 
	 * @param user
	 *            the user
	 */
	public void join(MobPlayer user) {
		player = AppContext.getDataManager().createReference(user);
		PlayerPositionMessage jppm = new PlayerPositionMessage();
		jppm.userName = user.getName();
		jppm.playerClass = user.getPlayerClass();
		jppm.x = (short) user.getPosition().getX();
		jppm.y = (short) user.getPosition().getY();
		HashSet<Player> inRange = user.getZone().getPlayersInRange(user,
				Constants.VISIBILITY_RANGE);
		for (Player u : inRange) {
			if (u.getMode().getType() == GameModes.MAP_MODE) {
				u.send(Protocol.encode(jppm));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.gamemode.GameMode#leave()
	 */
	@Override
	public void leave() {
		MobPlayer u = player.get();
		PlayerDisconnected pd = new PlayerDisconnected();
		pd.playerName = u.getName();
		HashSet<Player> inRange = u.getZone().getPlayersInRange(u,
				Constants.VISIBILITY_RANGE);
		for (Player user : inRange) {
			if (user.getMode().getType() == GameModes.MAP_MODE) {
				user.send(Protocol.encode(pd));
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.firegnom.valkyrie.server.gamemode.GameMode#getType()
	 */
	@Override
	public int getType() {
		return GameModes.MAP_MODE;
	}

}
