package client.map.state;

import shared.model.ClientModelController;
import shared.model.Road;
import shared.model.VertexObject;
import client.map.MapController;

public interface IMapState {
	public void initialize(MapController mapController);

	public String getClassName();

	public boolean canPlaceSettlement(VertexObject settlement,
			boolean playingCard, ClientModelController clientModelController);

	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelController clientModelController);

	public void beginRound(MapController mapController);
}
