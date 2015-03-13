package server.commands;

import shared.communication.ChatMessage;

/**
 * @author Drewfus
 * This is the command class for the SendChat function called on the server.
 * It will receive a ChatMessage object and a gameID in the constructor
 */

public class SendChatCommand implements ICommand {
	
	ChatMessage params;
	int gameID;
	
	SendChatCommand(ChatMessage params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Adds a MessageLine to the MessageList in the server model
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}