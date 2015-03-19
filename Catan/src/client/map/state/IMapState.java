package client.map.state;

import shared.model.Road;
import shared.model.VertexObject;
import client.map.MapController;
import client.model.ClientModelFacade;

public interface IMapState {
	public void initialize(MapController mapController);

	public String getClassName();

	public boolean canPlaceSettlement(VertexObject settlement,
			boolean playingCard, ClientModelFacade clientModelController);

	public boolean canPlaceRoad(int playerIndex, Road road, boolean isFree,
			ClientModelFacade clientModelController);

	public void beginRound(MapController mapController);
}
