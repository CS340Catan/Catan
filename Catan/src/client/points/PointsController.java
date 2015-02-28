package client.points;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.data.PlayerInfo;
import client.data.UserPlayerInfo;
import client.model.ClientModel;
import client.model.Player;

/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController,
		Observer {

	private IGameFinishedView finishedView;

	/**
	 * PointsController constructor
	 * 
	 * @param view
	 *            Points view
	 * @param finishedView
	 *            Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {

		super(view);

		setFinishedView(finishedView);

		initFromModel();
		ClientModel.getSingleton().addObserver(this);
	}

	public IPointsView getPointsView() {

		return (IPointsView) super.getView();
	}

	public IGameFinishedView getFinishedView() {
		return finishedView;
	}

	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		if (UserPlayerInfo.getSingleton().getName() != "") {
			int playerID = UserPlayerInfo.getSingleton().getPlayerIndex();
			// Get PlayerID from playerInfo class
			int victoryPoints = ClientModel.getSingleton().getPlayers()[playerID]
					.getVictoryPoints();
			getPointsView().setPoints(victoryPoints);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		/*
		 * TODO Check if this is how update would work for points controller.
		 * Need a way to find user's playerID.
		 */
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		int playerID = UserPlayerInfo.getSingleton().getId();
		int victoryPoints = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getVictoryPoints();
		getPointsView().setPoints(victoryPoints);

		for (Player player : ClientModel.getSingleton().getPlayers()) {
			if (player != null && player.getVictoryPoints() >= 10) {
				if (player.getPlayerid() == playerID) {
					finishedView.setWinner(player.getName(), true);
				} else {
					finishedView.setWinner(player.getName(), false);
				}
				finishedView.showModal();
				ClientModel.getSingleton().setWinner(player.getPlayerid());
				// TODO communicate to the map controller that a winner exists
			}
		}
	}
}
