package server.commands;

import server.facade.ServerFacade;
import server.model.*;
import shared.communication.PlayMonumentParams;
import shared.model.MessageLine;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the PlayMonument function called on the server.
 * It will receive a PlayMonumentParams object and a gameID in the constructor
 * 
 * @author Drewfus
 */

public class PlayMonumentCommand extends ICommand {

	int playerIndex;

	public PlayMonumentCommand(PlayMonumentParams params) {
		this.playerIndex = params.getPlayerIndex();
		this.setType("PlayMonument");
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
			
			/*
			 * Update game history
			 */
			String name = model.getPlayers()[playerIndex].getName();
			model.getLog().addLine(new MessageLine(name + " played a monument card.",name));

			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException("Unable to play monument card.");
		}
	}

}
