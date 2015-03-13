package client.map.state;

import shared.definitions.PieceType;
import shared.model.ClientModel;
import shared.model.ClientModelController;
import shared.model.Road;
import shared.model.VertexObject;
import client.data.UserPlayerInfo;
import client.map.MapController;

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
		int currentPlayerTurn = ClientModel.getSingleton().getTurnTracker()
				.getCurrentTurn();
		int clientPlayerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (!hasBegunRound && currentPlayerTurn == clientPlayerIndex) {
			hasBegunRound = true;
			if (ClientModel.getSingleton().getPlayers()[clientPlayerIndex]
					.getRoads() == 15) {
				mapController.startMove(PieceType.ROAD, true, true);
			}
			if (ClientModel.getSingleton().getPlayers()[clientPlayerIndex]
					.getSettlements() == 5) {
				mapController.startMove(PieceType.SETTLEMENT, true, true);
			}
		}
	}
}
