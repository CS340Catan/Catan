package client.map.state;

import client.map.MapController;

public interface IMapState {
	public void mapAction(MapController mapController);
	public String getClassName();
}
