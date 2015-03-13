package client.discard;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import shared.communication.DiscardCardsParams;
import shared.definitions.ResourceType;
import shared.model.ClientModel;
import shared.model.ClientModelController;
import shared.model.ResourceList;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.Controller;
import client.communicator.ServerProxy;
import client.data.UserPlayerInfo;
import client.misc.IWaitView;

/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements
		IDiscardController, Observer {

	private IWaitView waitView;
	private IServer serverProxy = ServerProxy.getSingleton();
	private ClientModelController modelController;
	private int amntToDiscard = 0;
	private int discardedAmnt = 0;

	private final String SERVER_ERROR = "Give us a minute to get the server working...";
	private final String NO_CAN_DO = "Sorry buster, no can do right now";

	/**
	 * DiscardController constructor
	 * 
	 * @param view
	 *            View displayed to let the user select cards to discard
	 * @param waitView
	 *            View displayed to notify the user that they are waiting for
	 *            other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {

		super(view);

		this.waitView = waitView;
		ClientModel.getNotifier().addObserver(this);
		modelController = new ClientModelController();
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView) super.getView();
	}

	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		// get current, decrease
		int max = getDiscardView().getResourceMaxAmount(resource);
		int newAmount = getDiscardView().getResourceDiscardAmount(resource) + 1;
		if (newAmount >= 0 && newAmount <= max) {
			getDiscardView().setResourceDiscardAmount(resource, newAmount);
			discardedAmnt++;
		}

		setAll();

		enableDiscard(discardedAmnt);

		getDiscardView().setStateMessage(
				"" + discardedAmnt + "/" + amntToDiscard);

	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		// get current, decrease
		int max = getDiscardView().getResourceMaxAmount(resource);
		int newAmount = getDiscardView().getResourceDiscardAmount(resource) - 1;
		if (newAmount >= 0 && newAmount <= max) {
			getDiscardView().setResourceDiscardAmount(resource, newAmount);
			discardedAmnt--;
		}

		setAll();

		enableDiscard(discardedAmnt);

		getDiscardView().setStateMessage(
				"" + discardedAmnt + "/" + amntToDiscard);

	}

	@Override
	public void discard() {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (modelController.canDiscardCards(playerIndex)) {
			// getResroucelist
			ResourceList list = getDiscardView().getListToDiscard();
			try {
				serverProxy.discardCards(new DiscardCardsParams(playerIndex,
						list));
			} catch (ServerResponseException e) {
				JOptionPane.showMessageDialog(null, SERVER_ERROR,
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, NO_CAN_DO, "No can do",
					JOptionPane.ERROR_MESSAGE);
		}

		getDiscardView().closeModal();
		if (!getWaitView().isModalShowing()) {
			waitView.showModal();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		ClientModel updatedModel = ClientModel.getSingleton();
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (updatedModel.getTurnTracker().getStatus().equals("Discarding")) {
			if (!getDiscardView().isModalShowing()
					&& !waitView.isModalShowing()) {
				if (modelController.canDiscardCards(playerIndex)) {

					setResource(ResourceType.BRICK, playerIndex);

					setResource(ResourceType.SHEEP, playerIndex);

					setResource(ResourceType.WHEAT, playerIndex);

					setResource(ResourceType.ORE, playerIndex);

					setResource(ResourceType.WOOD, playerIndex);

					// set main button
					getDiscardView().setDiscardButtonEnabled(false);
					getDiscardView().showModal();

					amntToDiscard = getTotalCount(playerIndex) / 2;
					discardedAmnt = 0;
					getDiscardView().setStateMessage("0/" + amntToDiscard);
				}

				else {
					if (!getWaitView().isModalShowing()) {
						waitView.showModal();
					}
				}
			}
		} else {
			if (getDiscardView().isModalShowing()) {
				getDiscardView().closeModal();
			}
			if (waitView.isModalShowing()) {
				waitView.closeModal();
			}
		}
	}

	private void setResource(ResourceType resource, int playerIndex) {
		int currentCount = getResourceCount(resource, playerIndex);
		getDiscardView().setResourceMaxAmount(resource, currentCount);
		getDiscardView().setResourceDiscardAmount(resource, 0);
		boolean canInc = false;
		if (currentCount > 0)
			canInc = true;
		getDiscardView()
				.setResourceAmountChangeEnabled(resource, canInc, false);
	}

	private int getTotalCount(int playerIndex) {
		int sum = getResourceCount(ResourceType.WOOD, playerIndex);
		sum += getResourceCount(ResourceType.ORE, playerIndex);
		sum += getResourceCount(ResourceType.BRICK, playerIndex);
		sum += getResourceCount(ResourceType.WHEAT, playerIndex);
		sum += getResourceCount(ResourceType.SHEEP, playerIndex);
		return sum;
	}

	private int getResourceCount(ResourceType resource, int playerIndex) {
		ClientModel updatedModel = modelController.getClientModel();
		switch (resource) {
		case WOOD:
			return updatedModel.getPlayers()[playerIndex].getResources()
					.getWood();
		case ORE:
			return updatedModel.getPlayers()[playerIndex].getResources()
					.getOre();
		case BRICK:
			return updatedModel.getPlayers()[playerIndex].getResources()
					.getBrick();
		case WHEAT:
			return updatedModel.getPlayers()[playerIndex].getResources()
					.getWheat();
		case SHEEP:
			return updatedModel.getPlayers()[playerIndex].getResources()
					.getSheep();
		default:
			return -1;
		}
	}

	private void enableDiscard(int newAmount) {
		boolean enable = (newAmount == amntToDiscard);

		getDiscardView().setDiscardButtonEnabled(enable);

		if (enable)
			disableAllUp();
	}

	private void disableAllUp() {

		int discWood = getDiscardView().getResourceDiscardAmount(
				ResourceType.WOOD);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD,
				false, discWood > 0);

		int discOre = getDiscardView().getResourceDiscardAmount(
				ResourceType.ORE);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE,
				false, discOre > 0);

		int discBrick = getDiscardView().getResourceDiscardAmount(
				ResourceType.BRICK);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK,
				false, discBrick > 0);

		int discWheat = getDiscardView().getResourceDiscardAmount(
				ResourceType.WHEAT);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT,
				false, discWheat > 0);

		int discSheep = getDiscardView().getResourceDiscardAmount(
				ResourceType.SHEEP);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP,
				false, discSheep > 0);
	}

	private void setAll() {
		int discWood = getDiscardView().getResourceDiscardAmount(
				ResourceType.WOOD);
		int maxWood = getDiscardView().getResourceMaxAmount(ResourceType.WOOD);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD,
				maxWood > discWood, discWood > 0);

		int discOre = getDiscardView().getResourceDiscardAmount(
				ResourceType.ORE);
		int maxOre = getDiscardView().getResourceMaxAmount(ResourceType.ORE);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE,
				maxOre > discOre, discOre > 0);

		int discBrick = getDiscardView().getResourceDiscardAmount(
				ResourceType.BRICK);
		int maxBrick = getDiscardView()
				.getResourceMaxAmount(ResourceType.BRICK);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK,
				maxBrick > discBrick, discBrick > 0);

		int discWheat = getDiscardView().getResourceDiscardAmount(
				ResourceType.WHEAT);
		int maxWheat = getDiscardView()
				.getResourceMaxAmount(ResourceType.WHEAT);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT,
				maxWheat > discWheat, discWheat > 0);

		int discSheep = getDiscardView().getResourceDiscardAmount(
				ResourceType.SHEEP);
		int maxSheep = getDiscardView()
				.getResourceMaxAmount(ResourceType.SHEEP);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP,
				maxSheep > discSheep, discSheep > 0);
	}

}
