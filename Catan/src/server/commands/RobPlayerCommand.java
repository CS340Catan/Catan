package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.MoveRobberParams;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;

/**
 * @author Drewfus This is the command class for the RobPlayer function called
 *         on the server. It will receive a MoveRobberParams object and a gameID
 *         in the constructor
 */

public class RobPlayerCommand implements ICommand {

	int playerIndex;
	int victimIndex;
	HexLocation location;

	public RobPlayerCommand(MoveRobberParams params) {
		this.playerIndex = params.getPlayerIndex();
		this.victimIndex = params.getVictimIndex();
		this.location = params.getLocation();
	}

	/**
	 * Moves the robber to the new location, steals a card from the indicated
	 * player.
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController modelController = new ServerModelController(model);

		if (modelController.canRobPlayer(this.location, playerIndex,
				this.victimIndex)) {
			Player player = model.getPlayers()[playerIndex];

			/*
			 * Move the robber to the appropriate position, appropriately steal
			 * a card from the victim and add appropriate resource to the
			 * player.
			 */
			model.getMap().setRobber(this.location);

			ResourceType stolenResource = stealResource(model.getPlayers()[this.victimIndex]);

			ResourceList playerResources = player.getResources();
			switch (stolenResource) {
			case BRICK:
				playerResources.setBrick(playerResources.getBrick() + 1);
				break;
			case ORE:
				playerResources.setOre(playerResources.getOre() + 1);
				break;
			case SHEEP:
				playerResources.setSheep(playerResources.getSheep() + 1);
				break;
			case WHEAT:
				playerResources.setWheat(playerResources.getWheat() + 1);
				break;
			case WOOD:
				playerResources.setWood(playerResources.getWood() + 1);
				break;
			default:
				break;
			}

			/*
			 * Set the state of the game from "Robbing" to "Playing" under
			 * status of the server model.
			 */
			model.getTurnTracker().setStatus("Playing");

		} else {
			throw new ServerResponseException("Unable to rob player.");
		}
	}

	private ResourceType stealResource(Player victimPlayer) {
		ResourceList victimResources = victimPlayer.getResources();
		ResourceType stealResource = victimResources
				.getRandomResourceFromList();

		switch (stealResource) {
		case BRICK:
			victimResources.setBrick(victimResources.getBrick() - 1);
			break;
		case ORE:
			victimResources.setOre(victimResources.getOre() - 1);
			break;
		case SHEEP:
			victimResources.setSheep(victimResources.getSheep() - 1);
			break;
		case WHEAT:
			victimResources.setWheat(victimResources.getWheat() - 1);
			break;
		case WOOD:
			victimResources.setWood(victimResources.getWood() - 1);
			break;
		default:
			break;
		}

		return stealResource;
	}

}
