package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.PlayMonopolyParams;
import shared.definitions.ResourceType;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;

/**
 * @author Drewfus This is the command class for the PlayMonopoly function
 *         called on the server. It will receive a PlayMonopolyParams object and
 *         a gameID in the constructor
 */

public class PlayMonopolyCommand implements ICommand {

	int playerIndex;
	ResourceType resource;

	public PlayMonopolyCommand(PlayMonopolyParams params) {
		this.resource = ResourceType.valueOf(params.getResource());
		this.playerIndex = params.getPlayerIndex();
	}

	/**
	 * Takes the desired resource card from all players, gives it to the player
	 * playing
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController modelController = new ServerModelController(model);

		if (modelController.canPlayMonopolyCard(playerIndex)) {
			Player player = model.getPlayers()[playerIndex];
			/*
			 * If the user can play a monument card, then get the current number
			 * of monuments in his or her hand. From this value, subtract one
			 * and reset the current player's monument count.
			 */
			int preMonopolyCard = player.getOldDevCards().getMonopoly();
			int postMonopolyCard = preMonopolyCard - 1;
			player.getOldDevCards().setMonopoly(postMonopolyCard);

			/*
			 * Given a resource string, steal from all other players resource
			 * hands that resource. For all players, steal the given resource
			 * and set the stolen players resources equal to zero. Add stolen
			 * resources to stored player.
			 */
			for (int i = 0; i < model.getPlayers().length; i++) {
				if (i == playerIndex)
					continue;

				Player stealPlayer = model.getPlayers()[i];
				switch (resource) {
				case WOOD:
					int stolenWood = stealPlayer.getResources().getWood();
					stealPlayer.getResources().setWood(0);
					player.getResources().setWood(
							player.getResources().getWood() + stolenWood);
					break;
				case BRICK:
					int stolenBrick = stealPlayer.getResources().getBrick();
					stealPlayer.getResources().setBrick(0);
					player.getResources().setBrick(
							player.getResources().getBrick() + stolenBrick);
					break;
				case SHEEP:
					int stolenSheep = stealPlayer.getResources().getSheep();
					stealPlayer.getResources().setSheep(0);
					player.getResources().setSheep(
							player.getResources().getSheep() + stolenSheep);
					break;
				case WHEAT:
					int stolenWheat = stealPlayer.getResources().getWheat();
					stealPlayer.getResources().setWheat(0);
					player.getResources().setWheat(
							player.getResources().getWheat() + stolenWheat);
					break;
				case ORE:
					int stolenOre = stealPlayer.getResources().getOre();
					stealPlayer.getResources().setOre(0);
					player.getResources().setOre(
							player.getResources().getOre() + stolenOre);
					break;
				default:
					break;
				}
			}
			
			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();
			
		} else {
			throw new ServerResponseException("Unable to play monopoly card.");
		}
	}
}
