package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.MoveSoldierParams;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.model.MessageLine;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the PlaySoldier function called on the server.
 * It will receive a MoveSoldierParams object and a gameID in the constructor
 * 
 * @author Drewfus
 */

public class PlaySoldierCommand extends ICommand {

	int playerIndex;
	int victimIndex;
	HexLocation location;

	public PlaySoldierCommand(MoveSoldierParams params) {
		this.playerIndex = params.getPlayerIndex();
		this.victimIndex = params.getVictimIndex();
		this.location = params.getLocation();
		this.setType("PlaySoldier");
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
		String oldStatus = model.getTurnTracker().getStatus();
		model.getTurnTracker().setStatus("Robbing");
		int i = 0;
		
		if (modelController.canPlaySoldierCard(this.location, playerIndex,
				this.victimIndex)) {
			model.getTurnTracker().setStatus(oldStatus);
			Player player = model.getPlayers()[playerIndex];

			/*
			 * If the user can play a soldier card, then get the current number
			 * of soldiers in his or her hand. From this value, subtract one and
			 * reset the current player's soldier count.
			 * 
			 * Also, add a soldier to the player's soldier count.
			 */
			int preSoldier = player.getOldDevCards().getSoldier();
			int postSoldier = preSoldier - 1;
			player.getOldDevCards().setSoldier(postSoldier);

			player.setSoldiers(player.getSoldiers() + 1);

			/*
			 * Move the robber to the appropriate position, appropriately steal
			 * a card from the victim and add appropriate resource to the
			 * player.
			 */
			model.getMap().setRobber(this.location);
			if (victimIndex != -1)
			{
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
			}

			/*
			 * Re-allocate the largest army card/points.
			 */
			model.reallocateLargestArmy();
			
			/*
			 * Set the player's has played development card boolean equal to
			 * true.
			 */
			player.setPlayedDevCard(true);

			/*
			 * Update game history
			 */
			String name = model.getPlayers()[playerIndex].getName();
			model.getLog().addLine(
					new MessageLine(name + " played a soldier card.", name));

			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException(
					"Unable to play soldier card. Invalid input parameters");
		}
	}

	private ResourceType stealResource(Player victimPlayer) {
		ResourceList victimResources = victimPlayer.getResources();
		ResourceType stealResource = victimResources
				.getRandomResourceFromList();

		/*
		 * Given a randomly selected resource from the victim, subtract one
		 * resource from the victim's resources.
		 */
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
