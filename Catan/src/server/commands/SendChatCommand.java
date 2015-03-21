package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.ChatMessage;
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
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		MessageLine messageLine = new MessageLine(params.getContent(),
				model.getPlayers()[params.getPlayerIndex()].getName());
		model.getChat().addLine(messageLine);
		
		/*
		 * Add this command to the list of commands currently stored inside
		 * the model.
		 */
		model.getCommands().add(this);
		model.incrementVersion();
	}

}