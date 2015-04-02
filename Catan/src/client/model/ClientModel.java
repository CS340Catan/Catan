package client.model;

import client.data.UserPlayerInfo;
import shared.model.AbstractModel;
import shared.model.Notifier;
import shared.model.Player;
import shared.utils.Serializer;

/**
 * Contains all data relevant to the game
 * 
 * <pre>
 * <b>Domain:</b>
 * -bank:ResourceList
 * -chat:MessageList
 * -log:MessageList
 * -map:Map
 * -players:[Player]
 * -tradeOffer:TradeOffer
 * -turnTracker:TurnTracker
 * -version:int
 * -winner:int
 * </pre>
 * 
 * @author Seth White
 *
 */
public class ClientModel extends AbstractModel {
	public static Notifier notifier = null;
	private static ClientModel clientModel = null;// singleton instance of
													// ClientModel

	/**
	 * Default constructor
	 */
	public ClientModel() {
		// empty constructor defeats instantiation
	}

	public static Notifier getNotifier() {
		if (notifier == null) {
			notifier = new Notifier();
		}
		return notifier;
	}

	public static ClientModel getSingleton() {// returns the singleton
		if (clientModel == null) {
			clientModel = new ClientModel();
		}
		return clientModel;
	}

	private void setUserPlayerInfoIndex(ClientModel clientModel) {
		for (Player player : clientModel.getPlayers()) {
			UserPlayerInfo upi = UserPlayerInfo.getSingleton();
			if (player != null && player.getName().equals(upi.getName())) {
				UserPlayerInfo.getSingleton().setPlayerIndex(
						player.getPlayerIndex());
			}
		}
	}

	public void setClientModel(ClientModel clientModel) {
		ClientModel.clientModel = clientModel;
		setUserPlayerInfoIndex(clientModel);
		if (notifier != null)
			notifier.modelUpdated();
	}

	public String toString() {
		return Serializer.serializeClientModel(this);
	}

}
