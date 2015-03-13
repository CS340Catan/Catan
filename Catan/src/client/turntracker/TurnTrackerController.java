package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import shared.communication.UserActionParams;
import shared.definitions.CatanColor;
import shared.model.ClientModel;
import shared.model.ClientModelController;
import shared.model.Player;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.ServerProxy;
import client.data.UserPlayerInfo;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements
		ITurnTrackerController, Observer {
	private ClientModelController clientModelController;
	private ITurnTrackerControllerState state = new TurnTrackerInitialState();

	public TurnTrackerController(ITurnTrackerView view) {

		super(view);

		ClientModel.getNotifier().addObserver(this);
		clientModelController = new ClientModelController();
	}

	@Override
	public ITurnTrackerView getView() {

		return (ITurnTrackerView) super.getView();
	}

	@Override
	public void endTurn() {
		getView().updateGameState("Waiting for other players", false);
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		UserActionParams finishTurn = new UserActionParams(playerIndex);
		finishTurn.setType("finishTurn");

		try {
			ServerProxy.getSingleton().finishTurn(finishTurn);
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// initialize players, but only if in initial state
		state.initFromModel(getView(), this);

		clientModelController = new ClientModelController();
		ClientModel model = ClientModel.getSingleton();
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();

		// set button
		if (playerIndex == model.getTurnTracker().getCurrentTurn())
			getView().updateGameState("End Turn", true);
		else
			getView().updateGameState("Waiting for other players", false);

		/*
		 * If the game is in the setup phases, make it so that the end turn
		 * button cannot be pressed.
		 */
		String currentStatus = model.getTurnTracker().getStatus();
		if (currentStatus.equals("FirstRound")
				|| currentStatus.equals("SecondRound")) {
			getView().updateGameState("Setup Phase", false);
		}

		// set color
		CatanColor playerColor = model.getPlayers()[playerIndex]
				.getPlayerInfo().getColor();
		getView().setLocalPlayerColor(playerColor);

		// get players, then update them all in the view
		Player[] players = model.getPlayers();
		for (Player player : players) {
			if (player != null) {
				boolean highlight = clientModelController.isPlayerTurn(player
						.getPlayerIndex());
				boolean largestArmy = clientModelController
						.hasLargestArmy(player.getPlayerIndex());
				boolean longestRoad = clientModelController
						.hasLongestRoad(player.getPlayerIndex());

				getView().updatePlayer(player.getPlayerIndex(),
						player.getVictoryPoints(), highlight, largestArmy,
						longestRoad, player.getCatanColor());
			}
		}

		getView().redrawAll();
	}

	public void setState(ITurnTrackerControllerState state) {
		this.state = state;
	}

}
