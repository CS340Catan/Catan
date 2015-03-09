package shared.communication;

import shared.locations.VertexLocation;

/**
 * Class which contains data for 'moves/buildCity'
 * 
 * @author winstonhurst
 *
 */
public class BuildCityParams {

	String type = "buildCity";
	/**
	 * Who's placing the city,
	 */
	int playerIndex;
	VertexLocationParam vertexLocation;

	public BuildCityParams(int playerIndex, VertexLocation vertexLocation) {
		vertexLocation.convertToPrimitives();
		this.playerIndex = playerIndex;
		this.vertexLocation = new VertexLocationParam();
		this.vertexLocation.setDirection(vertexLocation.getDirection());
		this.vertexLocation.setX(vertexLocation.getX());
		this.vertexLocation.setY(vertexLocation.getY());
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public VertexLocationParam getVertexLocation() {
		return vertexLocation;
	}

	public void setVertexLocation(VertexLocationParam vertexLocation) {
		this.vertexLocation = vertexLocation;
	}

	public String getType() {
		return type;
	}

}
