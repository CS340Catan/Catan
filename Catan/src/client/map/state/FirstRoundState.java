package client.map.state;

import shared.definitions.PieceType;
import client.map.MapController;
import client.model.ClientModelController;
import client.model.Road;
import client.model.VertexObject;

public class FirstRoundState implements IMapState {
	private final String CLASS_NAME = "FirstRoundState";

	@Override
	public void initialize(MapController mapController) {

	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean canPlaceSettlement(VertexObject settlement,
			boolean playingCard, ClientModelController clientModelController) {
		return clientModelController.canBuildSettlement(settlement, true, true);
	}

	@Override
	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelController clientModelController) {
		return clientModelController.canBuildRoad(playerIndex, road, true);
	}

	@Override
	public void beginRound(MapController mapController) {
		mapController.startMove(PieceType.SETTLEMENT, true, true);
		mapController.startMove(PieceType.ROAD, true, false);
	}
}
