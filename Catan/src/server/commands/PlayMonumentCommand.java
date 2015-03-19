package server.commands;

import client.model.ClientModel;
import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.PlayMonumentParams;

/**
 * @author Drewfus
 * This is the command class for the PlayMonument function called on the server.
 * It will receive a PlayMonumentParams object and a gameID in the constructor
 */

public class PlayMonumentCommand implements ICommand {
	
	int playerIndex;
	
	public PlayMonumentCommand(PlayMonumentParams params) {
		this.playerIndex = params.getPlayerIndex();
	}
	/**
	 * Gives the player the victory points, should set the game to say he won
	 */
	@Override
	public void execute() {
		ServerModel model = ServerFacade.getSingleton().getServerModel();

	}

}

