package server.commands;

import shared.communication.DiscardCardsParams;

/**
 * 
 * @author winstonhurst
 *This command removes resource cards from the players hand
 */
public class DiscardCardsCommand implements ICommand {
	DiscardCardsParams params;
	int gameID;
	
	/**
	 * 
	 * @param params - list of cards to discard and the id of the player discarding
	 */
	public DiscardCardsCommand(DiscardCardsParams params, int gameID){
		this.params = params;
		this.gameID = gameID;
	}
	
	/**
	 * Removes the cards from the player's hand.
	 * If all players have are done discarding, change game status to 'Playing'
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
