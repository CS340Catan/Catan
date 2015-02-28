package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import shared.communication.UserActionParams;
import shared.definitions.CatanColor;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.ServerProxy;
import client.data.PlayerInfo;
import client.data.UserPlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.Player;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements
		ITurnTrackerController, Observer {
	private ClientModelController clientModelController;
	private ITurnTrackerControllerState state = new TrackerInitialState();

	public TurnTrackerController(ITurnTrackerView view) {

		super(view);


		ClientModel.getSingleton().addObserver(this);
		clientModelController = new ClientModelController();
	}

	@Override
	public ITurnTrackerView getView() {

		return (ITurnTrackerView) super.getView();
	}

	@Override
	public void endTurn() {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		UserActionParams finishTurn = new UserActionParams(playerIndex);
		finishTurn.setType("finishTurn");
		
		try {
			ServerProxy.getSingleton().finishTurn(finishTurn);
		} catch (ServerResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		//initialize players, but only if in initial state
		state.initFromModel(getView(), this);
		
		clientModelController = new ClientModelController();
		ClientModel model = ClientModel.getSingleton();
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		
		//set color
		CatanColor color = model.getPlayers()[playerIndex].getPlayerInfo().getColor();
		getView().setLocalPlayerColor(color);
		
		//get players, then update them all in the view
		Player[] players = model.getPlayers();
		for(Player player: players) {
			boolean highlight = clientModelController.isPlayerTurn(player.getPlayerIndex());
			boolean largestArmy = clientModelController.hasLargestArmy(player.getPlayerIndex());
			boolean longestRoad = clientModelController.hasLongestRoad(player.getPlayerIndex());
			getView().updatePlayer(player.getPlayerIndex(), player.getVictoryPoints(), highlight, largestArmy, longestRoad);
			
		}

	}
	
	public void setState(ITurnTrackerControllerState state) {
		this.state = state;
	}

}
