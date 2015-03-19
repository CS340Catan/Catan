package client.map.state;

import shared.model.Road;
import shared.model.VertexObject;
import client.map.MapController;
import client.model.ClientModelFacade;

public class PlayingState implements IMapState {

	@Override
	public void initialize(MapController mapController) {

	}

	@Override
	public String getClassName() {
		return "PlayingState";
	}

	@Override
	public boolean canPlaceSettlement(VertexObject settlement,
			boolean playingCard, ClientModelFacade clientModelController) {
		return clientModelController.canBuildSettlement(settlement,
				playingCard, false);
	}

	@Override
	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelFacade clientModelController) {
		return clientModelController.canBuildRoad(playerIndex, road, isFree,
				false);
	}

	@Override
	public void beginRound(MapController mapController) {
		// TODO Auto-generated method stub

	}

}
