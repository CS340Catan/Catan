package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.ChatMessage;
import shared.model.MessageLine;

/**
 * This is the command class for the SendChat function called on the server. It
 * will receive a ChatMessage object and a gameID in the constructor
 * 
 * @author Drewfus
 */

public class SendChatCommand extends ICommand {

	int playerIndex;
	String content;

	public SendChatCommand(ChatMessage params) {
		this.playerIndex = params.getPlayerIndex();
		this.content = params.getContent();
		this.setType("SendChat");
	}

	/**
	 * Adds a MessageLine to the MessageList in the server model
	 */
	@Override
	public void execute() {
		ServerModel model = ServerFacade.getSingleton().getServerModel();

		/*
		 * Given the input parameters for the command, create a messageLine
		 * object which is then stored within the chat command.
		 */
		MessageLine messageLine = new MessageLine(this.content,
				model.getPlayers()[this.playerIndex].getName());
		model.getChat().addLine(messageLine);

		/*
		 * Add this command to the list of commands currently stored inside the
		 * model.
		 */
		model.getCommands().add(this);
		model.incrementVersion();
	}

}