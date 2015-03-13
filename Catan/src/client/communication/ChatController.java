package client.communication;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import client.base.*;
import client.communicator.ServerProxy;
import shared.definitions.*;
import shared.model.ClientModel;
import shared.model.MessageLine;
import shared.model.MessageList;
import shared.model.Player;
import shared.utils.IServer;
import shared.utils.ServerResponseException;

/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController,
		Observer {

	private IServer server;

	public ChatController(IChatView view) {
		super(view);
		server = ServerProxy.getSingleton();
		ClientModel.getNotifier().addObserver(this);
	}

	@Override
	public IChatView getView() {
		return (IChatView) super.getView();
	}

	@Override
	public void sendMessage(String message) {
		try {
			server.sendChat(message);
		} catch (ServerResponseException e) {
			/*
			 * If the server throws an exception, i.e. the user is not connected
			 * to the server.
			 */
			String outputStr = "Could not reach the server. Please try again later.";
			JOptionPane.showMessageDialog(null, outputStr, "Server Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		MessageList chatMessageList = ClientModel.getSingleton().getChat();

		ArrayList<LogEntry> logEntries = new ArrayList<LogEntry>();
		if (chatMessageList != null) {
			for (MessageLine chatMessage : chatMessageList.getLines()) {
				String messageString = chatMessage.getMessage();
				String messageSource = chatMessage.getSource();

				CatanColor messageColor = null;
				for (Player player : ClientModel.getSingleton().getPlayers()) {
					if (player.getName().equals(messageSource)) {
						messageColor = player.getCatanColor();
					}
				}

				logEntries.add(new LogEntry(messageColor, messageString));
			}
		}
		getView().setEntries(logEntries);
	}

}
