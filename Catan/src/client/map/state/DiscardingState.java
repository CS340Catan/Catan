package client.map.state;

import client.map.MapController;
import client.model.ClientModelController;
import client.model.Road;
import client.model.VertexObject;

public class DiscardingState implements IMapState {

	@Override
	public void initialize(MapController mapController) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canPlaceSettlement(VertexObject settlement, boolean playingCard,
			ClientModelController clientModelController) {
		return clientModelController.canBuildSettlement(settlement, playingCard, false);
	}

	@Override
	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelController clientModelController) {
		return clientModelController.canBuildRoad(playerIndex, road, isFree);
	}

	

}
