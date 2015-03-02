package client.communication;

import java.util.*;

import client.base.*;
import client.model.ClientModel;
import client.model.MessageLine;
import client.model.Player;
import shared.definitions.*;

/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements
		IGameHistoryController, Observer {

	public GameHistoryController(IGameHistoryView view) {

		super(view);
		initFromModel();
		ClientModel.getNotifier().addObserver(this);
	}

	@Override
	public IGameHistoryView getView() {

		return (IGameHistoryView) super.getView();
	}

	private void initFromModel() {
		/*
		 * Sample of how to create a new gameHistory entry.
		 * 
		 * entries.add(new LogEntry(CatanColor.BROWN,"Message"));
		 */

		List<LogEntry> entries = new ArrayList<LogEntry>();
		if (ClientModel.getSingleton().getLog() != null) {
			for (MessageLine historyMessage : ClientModel.getSingleton()
					.getLog().getLines()) {
				String messageString = historyMessage.getMessage();
				String messageSource = historyMessage.getSource();

				CatanColor messageColor = null;
				for (Player player : ClientModel.getSingleton().getPlayers()) {
					if (player.getName().equals(messageSource)) {
						messageColor = player.getCatanColor();
					}
				}

				entries.add(new LogEntry(messageColor, messageString));
			}
		}

		getView().setEntries(entries);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
	}

}
