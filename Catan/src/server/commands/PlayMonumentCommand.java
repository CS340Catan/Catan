package server.commands;

import server.facade.ServerFacade;
import server.model.*;
import shared.communication.PlayMonumentParams;
import shared.utils.ServerResponseException;

/**
 * @author Drewfus This is the command class for the PlayMonument function
 *         called on the server. It will receive a PlayMonumentParams object and
 *         a gameID in the constructor
 */

public class PlayMonumentCommand implements ICommand {

	int playerIndex;

	public PlayMonumentCommand(PlayMonumentParams params) {
		this.playerIndex = params.getPlayerIndex();
	}

	/**
	 * Gives the player the victory points, should set the game to say he won
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController modelController = new ServerModelController(model);

		if (modelController.canPlayMonumentCard(this.playerIndex)) {
			/*
			 * If the user can play a monument card, then get the current number
			 * of monuments in his or her hand. From this value, subtract one
			 * and reset the current player's monument count.
			 */
			int preMonumentCount = model.getPlayers()[playerIndex]
					.getOldDevCards().getMonument();
			int postMonumentCount = preMonumentCount - 1;
			model.getPlayers()[playerIndex].getOldDevCards().setMonument(
					postMonumentCount);

			/*
			 * Update the player's points, by adding one for the monument just
			 * played.
			 */
			int preVictoryPoints = model.getPlayers()[playerIndex]
					.getVictoryPoints();
			int postVictoryPoints = preVictoryPoints + 1;
			model.getPlayers()[playerIndex].setVictoryPoints(postVictoryPoints);
		} else {
			throw new ServerResponseException("Unable to play monument card.");
		}
	}

}
