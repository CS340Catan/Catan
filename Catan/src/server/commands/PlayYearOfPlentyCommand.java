package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.YearOfPlentyParams;
import shared.definitions.ResourceType;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the PlayYearOfPlenty function called on the
 * server. It will receive a YearOfPlentyParams object and a gameID in the
 * constructor
 * 
 * @author Drewfus
 */

public class PlayYearOfPlentyCommand extends ICommand {

	int playerIndex;
	ResourceType resource_1;
	ResourceType resource_2;

	public PlayYearOfPlentyCommand(YearOfPlentyParams params) {
		this.playerIndex = params.getPlayerIndex();
		this.resource_1 = ResourceType.valueOf(params.getResource1());
		this.resource_2 = ResourceType.valueOf(params.getResource2());
		this.setType("PlayYearOfPlenty");
	}

	/**
	 * Gives the indicated player his resources of choice
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController modelController = new ServerModelController(model);

		ResourceList requestedResources = this.getRequestedResourceList();

		if (modelController.canPlayYearOfPlentyCard(playerIndex,
				requestedResources)) {
			Player player = model.getPlayers()[playerIndex];

			/*
			 * If the user can play a year of plenty card, then get the current
			 * number of monuments in his or her hand. From this value, subtract
			 * one and reset the current player's year of plenty count.
			 */
			int preYearOfPlenty = player.getOldDevCards().getYearOfPlenty();
			int postYearOfPlenty = preYearOfPlenty - 1;
			player.getOldDevCards().setMonopoly(postYearOfPlenty);

			/*
			 * Given the resource types inputed into the command, remove 1 of
			 * each from the bank and transfer those cards to the player's hand.
			 */
			model.addResourceFromBank(playerIndex, resource_1, 1);
			model.addResourceFromBank(playerIndex, resource_2, 1);

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

	private ResourceList getRequestedResourceList() {
		/*
		 * Converts the inputted resource types for the command into an
		 * appropriate resource list, which can then be added or removed from
		 * the bank.
		 */
		int brick = 0;
		int ore = 0;
		int sheep = 0;
		int wheat = 0;
		int wood = 0;

		switch (this.resource_1) {
		case BRICK:
			brick++;
			break;
		case ORE:
			ore++;
			break;
		case SHEEP:
			sheep++;
			break;
		case WHEAT:
			wheat++;
			break;
		case WOOD:
			wood++;
			break;
		default:
			break;
		}

		switch (this.resource_2) {
		case BRICK:
			brick++;
			break;
		case ORE:
			ore++;
			break;
		case SHEEP:
			sheep++;
			break;
		case WHEAT:
			wheat++;
			break;
		case WOOD:
			wood++;
			break;
		default:
			break;

		}
		return new ResourceList(brick, ore, sheep, wheat, wood);
	}
}