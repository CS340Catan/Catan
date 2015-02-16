package client.communication;

import java.awt.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.MessageLine;
import client.model.MessageList;
import shared.definitions.*;

/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer {

	public ChatController(IChatView view) {
		super(view);
		ClientModel.getSingleton().addObserver(this);
	}

	@Override
	public IChatView getView() {
		return (IChatView) super.getView();
	}

	@Override
	public void sendMessage(String message) {
		// Send the playerindex as the source
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ClientModelController clientModelController = new ClientModelController();
		MessageList messageList = ClientModel.getSingleton().getChat();
		ChatView chatView = (ChatView) this.getView();
		ArrayList<LogEntry> logEntries = new ArrayList<LogEntry>();

		for (MessageLine messageLine : messageList.getLines()) {
			LogEntry logEntry = new LogEntry(clientModelController.getPlayerColor(Integer.parseInt(messageLine.getSource())), messageLine.getMessage());
			logEntries.add(logEntry);
		}
		chatView.setEntries(logEntries);
	}

}
