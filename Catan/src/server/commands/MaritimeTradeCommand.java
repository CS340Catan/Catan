package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.MaritimeTradeParams;
import shared.definitions.ResourceType;
import shared.model.MessageLine;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the MaritimeTrade function called on the
 * server. It will receive a MaritimeTradeParams object and a gameID in the
 * constructor
 * 
 * @author Drewfus
 */

public class MaritimeTradeCommand extends ICommand {

	int playerIndex;
	ResourceType input;
	ResourceType output;
	int ratio;
	int gameID;

	public MaritimeTradeCommand(MaritimeTradeParams params, int gameID) {

		this.playerIndex = params.getPlayerIndex();
		this.input = ResourceType.valueOf(params.getInputResource().toUpperCase());
		this.output = ResourceType.valueOf(params.getOutputResource().toUpperCase());
		this.ratio = params.getRatio();
		this.gameID = gameID;
		this.setType("MaritimeTrade");
	}

	/**
	 * Trades with the bank with the desired resources
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		if (controller.canMaritimeTrade(playerIndex, input, ratio)) {
			/*
			 * If you can maritime trade, then take from the user the
			 * appropriate number of resources and add that to the bank. Then
			 * take the appropriate resource from the bank and add to the
			 * resource.
			 */
			model.addResourceFromBank(playerIndex, input, -ratio);
			model.addResourceFromBank(playerIndex, output, 1);
			
			/*
			 * Update game history
			 */
			String name = model.getPlayers()[playerIndex].getName();
			model.getLog().addLine(new MessageLine(name + " traded by sea",name));

			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException(
					"Unable to maritime trade. Invalid input parameters.");
		}

	}

}
