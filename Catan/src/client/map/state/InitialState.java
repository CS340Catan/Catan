package client.map.state;

import shared.model.Road;
import shared.model.VertexObject;
import client.map.MapController;
import client.model.ClientModelFacade;

public class InitialState implements IMapState {

	@Override
	public void initialize(MapController mapController) {
		mapController.populateHexes();
		mapController.populateWaterHexes();
		mapController.populatePorts();
	}

	@Override
	public String getClassName() {
		return "InitialState";
	}

	@Override
	public boolean canPlaceSettlement(VertexObject settlement,
			boolean playingCard, ClientModelFacade clientModelController) {
		return false;
	}

	@Override
	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelFacade clientModelController) {
		return false;
	}

	@Override
	public void beginRound(MapController mapController) {
		// TODO Auto-generated method stub

	}

}
