package client.map.state;

import client.map.MapController;
import client.model.ClientModelController;
import client.model.Road;
import client.model.VertexObject;

public class InitialState implements IMapState {

	@Override
	public void initialize(MapController mapController) {
		mapController.populateHexes();
		mapController.populateWaterHexes();
		mapController.populatePorts();
	}

	@Override
	public String getClassName() {
		return null;
	}

	@Override
	public boolean canPlaceSettlement(VertexObject settlement,
			boolean playingCard, ClientModelController clientModelController) {
		return false;
	}

	@Override
	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelController clientModelController) {
		return false;
	}

}
