package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.UserActionParams;
import shared.model.DevCardList;

/**
 * 
 * @author winstonhurst
 *This command finishes a player's turn
 */
public class FinishTurnCommand implements ICommand {
	
	int playerIndex;
	int gameID;
	
	/**
	 * 
	 * @param params - contains the player's index in the game
	 * @param gameID - id of the game on which to act
	 */
	public FinishTurnCommand(UserActionParams params, int gameID){
		
		this.playerIndex = params.getPlayerIndex();
		this.gameID = gameID;
	}
	
	/**
	 * Changes current player turn to next player's id.
	 * Changes the status of the game associated with the gameID to Rolling.
	 */
	@Override
	public void execute() {
		
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		
		// adjust dev cards
		DevCardList newCards = model.getPlayers()[playerIndex].getNewDevCards();
		model.getPlayers()[playerIndex].setOldDevCards(newCards);

	}

}

