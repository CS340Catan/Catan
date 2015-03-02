package client.map.state;

import shared.definitions.PieceType;
import client.map.MapController;
import client.model.ClientModelController;
import client.model.Road;
import client.model.VertexObject;

public class SecondRoundState implements IMapState {
	private final String CLASS_NAME = "SecondRoundState";
	private boolean hasBegunRound = false;
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
		return clientModelController.canBuildRoad(playerIndex, road, true, true);
	}

	@Override
	public void beginRound(MapController mapController) {
		if(!hasBegunRound){
			hasBegunRound = true;
			mapController.startMove(PieceType.ROAD, true, false);		
			mapController.startMove(PieceType.SETTLEMENT, true, true);
		}
	}

}
