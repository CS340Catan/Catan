package server.commands;

import java.util.*;
import java.util.Map;

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
 * @author Drewfus This is the command class for the RollNumber function called
 *         on the server. It will receive a RollParams object and a gameID in
 *         the constructor
 */

public class RollNumberCommand implements ICommand {

	int playerIndex;
	int number;

	public RollNumberCommand(RollParams params) {

		this.playerIndex = params.getPlayerIndex();
		this.number = params.getNumber();
	}

	/**
	 * Updates each players' resources according to what number was rolled
	 * @throws ServerResponseException 
	 */
	@Override
	public void execute() throws ServerResponseException {
				ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		if (controller.canRollNumber(playerIndex)) {

			if (number == 7) {

				String status;
				if (model.needToDiscard()) {
					status = "Discarding";
				} else {
					status = "Robbing";
				}
				model.getTurnTracker().setStatus(status);

			} else {
				// go around all hexes, if number matches then give resource(s)
				// to people living on vertexes
				Hex[] hexes = model.getMap().getHexes();

				for (Hex hex : hexes) {
					if (hex.getNumber() == number) {

						// give resources to everyone living here
						HexLocation location = hex.getLocation();
						ResourceType resource = ResourceType.valueOf(hex
								.getResource());

						// create 6 locations, one at each vertex, test it
						VertexObject testLocation = new VertexObject(-1,
								new VertexLocation(location,
										VertexDirection.NorthWest));
						checkLocation(testLocation, resource);

						testLocation.setLocation(new VertexLocation(location,
								VertexDirection.NorthEast));
						checkLocation(testLocation, resource);

						testLocation.setLocation(new VertexLocation(location,
								VertexDirection.East));
						checkLocation(testLocation, resource);

						testLocation.setLocation(new VertexLocation(location,
								VertexDirection.SouthEast));
						checkLocation(testLocation, resource);

						testLocation.setLocation(new VertexLocation(location,
								VertexDirection.SouthWest));
						checkLocation(testLocation, resource);

						testLocation.setLocation(new VertexLocation(location,
								VertexDirection.West));
						checkLocation(testLocation, resource);

					}
				}

			}

		}
		else {
			throw new ServerResponseException("Unable to roll number");
		}

	}

	private void checkLocation(VertexObject testLocation, ResourceType type) {

		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		int owner = controller.settlementOwner(testLocation);
		if (owner != -1) {
			// add 1 to the map
			model.addResourceFromBank(owner, type, 1);
		}

		owner = controller.cityOwner(testLocation);
		if (owner != -1) {
			// add 2 to the map
			model.addResourceFromBank(owner, type, 2);
		}
	}

}
