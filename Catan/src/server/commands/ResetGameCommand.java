package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.definitions.CatanColor;
import shared.model.DevCardList;
import shared.model.Player;
import shared.model.ResourceList;

/**
 * This is the command class for the ResetGame function called on the server. It
 * will receive a gameID in the constructor
 * 
 * @author Drewfus
 */

public class ResetGameCommand extends ICommand {

	int gameID;

	ResetGameCommand(int gameID) {
		this.gameID = gameID;
		this.setType("ResetGame");

	}

	/**
	 * Reverts the game back to how it was after initial setup
	 */
	@Override
	public void execute() {
		/*
		 * This will reset the game back to the original state after
		 * initialization. Create a newModel using the default serverModel
		 * constructor.
		 */
		ServerModel oldModel = ServerFacade.getSingleton().getServerModel();
		ServerModel newModel = new ServerModel();

		/*
		 * Save the oldModel's ports, hexes, and gameID to the new model, so
		 * that the map matches.
		 */
		newModel.getMap().setPorts(oldModel.getMap().getPorts());
		newModel.getMap().setHexes(oldModel.getMap().getHexes());
		newModel.setGameID(oldModel.getGameID());
		newModel.setWinner(-1);

		/*
		 * Save the oldModel's players to the new model, by creating a default
		 * empty player but matches playerindex, id, name and color.
		 */
		Player[] oldModelPlayers = oldModel.getPlayers();
		Player[] newModelPlayers = new Player[oldModelPlayers.length];
		for (int i = 0; i < oldModel.getPlayers().length; i++) {
			newModelPlayers[i] = new Player(
					oldModelPlayers[i].getPlayerIndex(),
					oldModelPlayers[i].getPlayerid(), 4, 5,
					oldModelPlayers[i].getName(),
					oldModelPlayers[i].getColor(), false, 0, new DevCardList(0,
							0, 0, 0, 0), new DevCardList(0, 0, 0, 0, 0), false,
					new ResourceList(0, 0, 0, 0, 0), 15, 0, 0);
		}

		/*
		 * Overwrite the oldModel with the newModel, such that the model stored
		 * within the serverFacade is the reset game.
		 */
		oldModel = newModel;
	}

}
