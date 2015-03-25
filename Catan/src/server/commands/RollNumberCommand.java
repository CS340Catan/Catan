package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.RollParams;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.*;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the RollNumber function called on the server.
 * It will receive a RollParams object and a gameID in the constructor
 * 
 * @author Drewfus
 */

public class RollNumberCommand extends ICommand {

	int playerIndex;
	int number;

	public RollNumberCommand(RollParams params) {
		this.playerIndex = params.getPlayerIndex();
		this.number = params.getNumber();
		this.setType("RollNumber");
	}

	/**
	 * Updates each players' resources according to what number was rolled
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		if (controller.canRollNumber(playerIndex)) {
			/*
			 * If the rolled number is a seven, then check if players need to
			 * discard. If a player needs to discard, then set the status of the
			 * turn tracker to discarding. Else, set the status to robbing.
			 */
			if (this.number == 7) {
				if (model.needToDiscard()) {
					model.getTurnTracker().setStatus("Discarding");
				} else {
					model.getTurnTracker().setStatus("Robbing");
				}
			}
			/*
			 * If the rolled number is not a seven, then call resourceRoll
			 * method which will add resources to appropriate players given
			 * their city and settlement locations.
			 */
			else {
				resourceRoll(model);
				model.getTurnTracker().setStatus("Playing");
			}

			/*
			 * Update game history
			 */
			String name = model.getPlayers()[playerIndex].getName();
			model.getLog().addLine(
					new MessageLine(name + " rolled a " + this.number + ".",
							name));

			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException("Unable to roll number");
		}
	}

	private void resourceRoll(ServerModel model) {
		/*
		 * Iterate through map's hexes. If the hex's number matches the inputed
		 * number, then add resource(s) to the user's who have vertex objects on
		 * the vertices of the hex.
		 */
		for (Hex hex : model.getMap().getHexes()) {
			if (hex.getNumber() == this.number
					&& !model.getMap().getRobber().equals(hex.getLocation())) {
				HexLocation hexLocation = hex.getLocation();
				ResourceType hexResource = ResourceType.valueOf(hex
						.getResource().toUpperCase());

				/*
				 * Create a test location, that will be updated such that it
				 * correlates to each of the 6 vertex directions. Check if the a
				 * player has a city or settlement on that vertex.
				 */
				VertexObject testLocation = new VertexObject(-1,
						new VertexLocation(hexLocation,
								VertexDirection.NorthWest));
				addResourcesIfObjectExists(testLocation, hexResource, model);

				testLocation.setLocation(new VertexLocation(hexLocation,
						VertexDirection.NorthEast));
				addResourcesIfObjectExists(testLocation, hexResource, model);

				testLocation.setLocation(new VertexLocation(hexLocation,
						VertexDirection.East));
				addResourcesIfObjectExists(testLocation, hexResource, model);

				testLocation.setLocation(new VertexLocation(hexLocation,
						VertexDirection.SouthEast));
				addResourcesIfObjectExists(testLocation, hexResource, model);

				testLocation.setLocation(new VertexLocation(hexLocation,
						VertexDirection.SouthWest));
				addResourcesIfObjectExists(testLocation, hexResource, model);

				testLocation.setLocation(new VertexLocation(hexLocation,
						VertexDirection.West));
				addResourcesIfObjectExists(testLocation, hexResource, model);
			}
		}
	}

	private void addResourcesIfObjectExists(VertexObject testLocation,
			ResourceType type, ServerModel model) {
		ServerModelController controller = new ServerModelController(model);

		/*
		 * Check if there exists a settlement at the testLocation. If there
		 * exists a settlement (returns a value other than -1), add 1 of the
		 * given resource type to the player's hand and remove 1 of the given
		 * resource type from the bank.
		 */
		int settlementOwner = controller.settlementOwner(testLocation);
		if (settlementOwner != -1) {
			model.addResourceFromBank(settlementOwner, type, 1);
		}

		/*
		 * Check if there exists a city at the testLocation. If there exists a
		 * city (returns a value other than -1), add 2 of the given resource
		 * type to the player's hand and remove 2 of the given resource type
		 * from the bank.
		 */
		int cityOwner = controller.cityOwner(testLocation);
		if (cityOwner != -1) {
			// add 2 to the map
			model.addResourceFromBank(cityOwner, type, 2);
		}
	}

}
