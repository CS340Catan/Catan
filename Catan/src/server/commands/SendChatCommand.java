package server.commands;

import server.facade.ServerFacade;
import shared.communication.ChatMessage;
import shared.model.ClientModel;
import shared.model.MessageLine;

/**
 * @author Drewfus
 * This is the command class for the SendChat function called on the server.
 * It will receive a ChatMessage object and a gameID in the constructor
 */

public class SendChatCommand implements ICommand {
	
	ChatMessage params;
	int gameID;
	
	public SendChatCommand(ChatMessage params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Adds a MessageLine to the MessageList in the server model
	 */
	@Override
	public void execute() {
		ClientModel clientModel = ServerFacade.getSingleton().getClientModel();
		MessageLine messageLine = new MessageLine(params.getContent(),
				clientModel.getPlayers()[params.getPlayerIndex()].getName());
		clientModel.getChat().addLine(messageLine);
	}

}