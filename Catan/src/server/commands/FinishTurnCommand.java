package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.UserActionParams;
import shared.model.DevCardList;
import shared.model.Player;
import shared.model.VertexObject;

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
		
		Player player = model.getPlayers()[playerIndex];
		DevCardList newCards = player.getNewDevCards();
		DevCardList oldCards = player.getOldDevCards();
		
		// add new cards to old cards
		oldCards.setMonopoly(oldCards.getMonopoly() + newCards.getMonopoly());
		oldCards.setMonument(oldCards.getMonument() + newCards.getMonument());
		oldCards.setRoadBuilding(oldCards.getRoadBuilding() + newCards.getRoadBuilding());
		oldCards.setMonopoly(oldCards.getMonopoly() + newCards.getMonopoly());
		oldCards.setYearOfPlenty(oldCards.getYearOfPlenty() + newCards.getYearOfPlenty());
		
		// reset new cards
		newCards.setMonopoly(0);
		newCards.setMonument(0);
		newCards.setRoadBuilding(0);
		newCards.setSoldier(0);
		newCards.setYearOfPlenty(0);
		
		// rotate to next player's turn
		int nextPlayer = playerIndex + 1;
		if(nextPlayer > 3) {
			nextPlayer = 0;
		}
		model.getTurnTracker().setCurrentTurn(nextPlayer);
		
	}

}

