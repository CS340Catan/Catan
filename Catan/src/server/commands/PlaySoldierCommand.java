package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.MoveSoldierParams;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;

/**
 * @author Drewfus This is the command class for the PlaySoldier function called
 *         on the server. It will receive a MoveSoldierParams object and a
 *         gameID in the constructor
 */

public class PlaySoldierCommand implements ICommand {

	int playerIndex;
	int victimIndex;
	HexLocation location;

	public PlaySoldierCommand(MoveSoldierParams params) {
		this.playerIndex = params.getPlayerIndex();
		this.victimIndex = params.getVictimIndex();
		this.location = params.getLocation();
	}

	/**
	 * Moves the robber to the new location, steals a card from the indicated
	 * player, updates the robbing player's development cards
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController modelController = new ServerModelController(model);

		if (modelController.canPlaySoldierCard(this.location, playerIndex,
				this.victimIndex)) {
			Player player = model.getPlayers()[playerIndex];

			/*
			 * If the user can play a year of plenty card, then get the current
			 * number of monuments in his or her hand. From this value, subtract
			 * one and reset the current player's year of plenty count.
			 */
			int preSoldier = player.getOldDevCards().getSoldier();
			int postSoldier = preSoldier - 1;
			player.getOldDevCards().setSoldier(postSoldier);

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

		} else {
			throw new ServerResponseException("Unable to play monument card.");
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
