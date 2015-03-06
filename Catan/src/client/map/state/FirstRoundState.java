package client.map.state;

import shared.definitions.PieceType;
import client.data.UserPlayerInfo;
import client.map.MapController;
import client.model.ClientModelController;
import client.model.Road;
import client.model.VertexObject;

public class FirstRoundState implements IMapState {
	private final String CLASS_NAME = "FirstRoundState";
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
		return clientModelController
				.canBuildRoad(playerIndex, road, true, true);
	}

	@Override
	public void beginRound(MapController mapController) {
		ClientModelController clientModelController = new ClientModelController();
		//TODO test
		if (!hasBegunRound
				&& clientModelController.isPlayerTurn(UserPlayerInfo
						.getSingleton().getId())) {
			hasBegunRound = true;
			mapController.startMove(PieceType.ROAD, true, true);
			mapController.startMove(PieceType.SETTLEMENT, true, true);

		}
	}
}
