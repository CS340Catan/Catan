package server.commands;

import client.model.ResourceList;
import shared.communication.DiscardCardsParams;

/**
 * Discards the cards of a given player
 * @author Seth White
 *
 */
public class DiscardCardsCommand implements ICommand {
	ResourceList resourceList; //TODO: fix resource list reference
	int playerIndex;
	String type;
	
	public DiscardCardsCommand(DiscardCardsParams params) {

	}

	/**
	 * Discards the cards of a given player
	 * @author Seth White
	 *
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
