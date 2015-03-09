package shared.communication;

import shared.locations.EdgeLocation;

/**
 * Class which contains data for 'moves/Road_Building'
 * 
 * @author winstonhurst
 *
 */
public class BuildRoadCardParams {

	String type = "Road_Building";
	/**
	 * who is placing the road
	 */
	int playerIndex;
	EdgeLocationParam spot1;
	EdgeLocationParam spot2;

	public BuildRoadCardParams(int playerIndex, EdgeLocation spot1,
			EdgeLocation spot2) {
		this.spot1 = new EdgeLocationParam();
		this.spot2 = new EdgeLocationParam();
		spot1.convertToPrimitives();
		spot2.convertToPrimitives();
		this.playerIndex = playerIndex;
		String thing = spot1.getDirection();
		this.spot1.setDirection(thing);
		this.spot1.setX(spot1.getX());
		this.spot1.setY(spot1.getY());

		this.spot2.setDirection(spot2.getDirection());
		this.spot2.setX(spot2.getX());
		this.spot2.setY(spot2.getY());
	}

	public String getType() {
		return type;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public EdgeLocationParam getSpot1() {
		return spot1;
	}

	public void setSpot1(EdgeLocationParam spot1) {
		this.spot1 = spot1;
	}

	public EdgeLocationParam getSpot2() {
		return spot2;
	}

	public void setSpot2(EdgeLocationParam spot2) {
		this.spot2 = spot2;
	}

}
