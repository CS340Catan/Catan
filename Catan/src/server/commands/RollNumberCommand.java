package server.commands;

import server.facade.ServerFacade;
import shared.communication.RollParams;
import shared.model.ClientModel;

/**
 * @author Drewfus
 * This is the command class for the RollNumber function called on the server.
 * It will receive a RollParams object and a gameID in the constructor
 */

public class RollNumberCommand implements ICommand {
	
	int playerIndex;
	int number;
	
	RollNumberCommand(RollParams params) {
		
		this.playerIndex = params.getPlayerIndex();
		this.number = params.getNumber();
	}
	/**
	 * Updates each players' resources according to what number was rolled
	 */
	@Override
	public void execute() {
		//assume current turn player called roll command :/
		
		ClientModel model = ServerFacade.getSingleton().getClientModel();
		
		int playerIndex = model.getTurnTracker().getCurrentTurn();
		
		

	}

}

