package client.communication;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.model.ClientModel;
import client.model.MessageList;
import shared.definitions.*;

/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer {
	private MessageList messageList;
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
		ChatView chatView = (ChatView) this.getView();
//		CatanColor color = new CatanColor();
		LogEntry logEntry = new LogEntry(null, message);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		messageList = ClientModel.getSingleton().getChat();
		System.out.println("cheeesesesesesess");
	}
	

}
