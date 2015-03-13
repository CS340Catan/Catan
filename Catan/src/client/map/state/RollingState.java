package client.map.state;

import shared.model.ClientModelController;
import shared.model.Road;
import shared.model.VertexObject;
import client.map.MapController;

public class RollingState implements IMapState {

	@Override
	public void initialize(MapController mapController) {

	}

	@Override
	public String getClassName() {
		return "RollingState";
	}

	@Override
	public boolean canPlaceSettlement(VertexObject settlement,
			boolean playingCard, ClientModelController clientModelController) {
		return clientModelController.canBuildSettlement(settlement,
				playingCard, false);
	}

	@Override
	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelController clientModelController) {
		return clientModelController.canBuildRoad(playerIndex, road, isFree,
				false);
	}

	@Override
	public void beginRound(MapController mapController) {
		// TODO Auto-generated method stub

	}

}
