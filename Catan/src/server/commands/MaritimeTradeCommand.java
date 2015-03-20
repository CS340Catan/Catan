package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.MaritimeTradeParams;
import shared.definitions.ResourceType;

/**
 * @author Drewfus
 * This is the command class for the MaritimeTrade function called on the server.
 * It will receive a MaritimeTradeParams object and a gameID in the constructor
 */

public class MaritimeTradeCommand implements ICommand {
	
	int playerIndex;
	ResourceType input;
	ResourceType output;
	int ratio;
	int gameID;
	
	public MaritimeTradeCommand(MaritimeTradeParams params, int gameID) {
		
		this.playerIndex = params.getPlayerIndex();
		this.input = ResourceType.valueOf(params.getInputResource());
		this.output = ResourceType.valueOf(params.getOutputResource());
		this.ratio = params.getRatio();
		this.gameID = gameID;
	}
	/**
	 * Trades with the bank with the desired resources
	 */
	@Override
	public void execute() {
		
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);
		
		if(controller.canMaritimeTrade(playerIndex, input, ratio)) {
			
			model.addResourceFromBank(playerIndex, input, ratio);
			model.addResourceFromBank(playerIndex, output, 1);
		}

	}

}

