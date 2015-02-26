package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import shared.definitions.CatanColor;
import client.base.*;
import client.data.PlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelController;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements
		ITurnTrackerController, Observer {
	private ClientModelController clientModelController;

	public TurnTrackerController(ITurnTrackerView view) {

		super(view);

		initFromModel();
		ClientModel.getSingleton().addObserver(this);
		clientModelController = new ClientModelController();
	}

	@Override
	public ITurnTrackerView getView() {

		return (ITurnTrackerView) super.getView();
	}

	@Override
	public void endTurn() {

	}

	private void initFromModel() {
//		getView().setLocalPlayerColor(clientModelController.getPlayerColor(PlayerInfo.getSingleton().getPlayerIndex()));
		getView().setLocalPlayerColor(CatanColor.RED);		
	}

	@Override
	public void update(Observable o, Object arg) {
		clientModelController = new ClientModelController();
		
	}

}
