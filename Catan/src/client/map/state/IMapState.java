package client.map.state;

import client.map.MapController;
import client.model.ClientModelController;
import client.model.Road;
import client.model.VertexObject;

public interface IMapState {
	public void initialize(MapController mapController);

	public String getClassName();

	public boolean canPlaceSettlement(VertexObject settlement,
			boolean playingCard, ClientModelController clientModelController);

	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelController clientModelController);
}
