package client.map.state;

import shared.definitions.PieceType;
import shared.model.Road;
import shared.model.VertexObject;
import client.data.UserPlayerInfo;
import client.map.MapController;
import client.model.ClientModel;
import client.model.ClientModelFacade;

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
			boolean playingCard, ClientModelFacade clientModelController) {
		return clientModelController.canBuildSettlement(settlement, true, true);
	}

	@Override
	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelFacade clientModelController) {
		return clientModelController
				.canBuildSecondRoad(playerIndex, road, true);
	}

	@Override
	public void beginRound(MapController mapController) {
		int currentPlayerTurn = ClientModel.getSingleton().getTurnTracker()
				.getCurrentTurn();
		int clientPlayerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (!hasBegunRound && currentPlayerTurn == clientPlayerIndex) {
			hasBegunRound = true;
			if (ClientModel.getSingleton().getPlayers()[clientPlayerIndex]
					.getRoads() == 14) {
				mapController.startMove(PieceType.ROAD, true, true);
			}
			if (ClientModel.getSingleton().getPlayers()[clientPlayerIndex]
					.getSettlements() == 4) {
				mapController.startMove(PieceType.SETTLEMENT, true, true);
			}
		}
	}

}
