package shared.communication;

import shared.locations.VertexLocation;
/**
 * Class which contains data for 'moves/buildSettlement'
 * @author winstonhurst
 *
 */
public class BuildSettlementParams {

	String type = "buildSettlement";
	/**
	 * Who's placing the settlement,
	 */
	int playerIndex;
	VertexLocationParam vertexLocation;
	/**
	 * Whether this is placed for free (setup)
	 */
	boolean free;
	
	public BuildSettlementParams(int playerIndex, VertexLocation vertexLocation, boolean free) {
		vertexLocation.convertToPrimitives();
		this.playerIndex = playerIndex;
		this.vertexLocation = new VertexLocationParam();
		this.vertexLocation.setDirection(vertexLocation.getDirection());
		this.vertexLocation.setX(vertexLocation.getX());
		this.vertexLocation.setY(vertexLocation.getY());
		this.free = free;
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

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public String getType() {
		return type;
	}
}
