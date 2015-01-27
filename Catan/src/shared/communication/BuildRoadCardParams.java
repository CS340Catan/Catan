package shared.communication;

import shared.locations.EdgeLocation;
/**
 * Class which contains data for 'moves/Road_Building'
 * @author winstonhurst
 *
 */
public class BuildRoadCardParams {

	String type = "Road_Building";
	/**
	 * who is placing the road
	 */
	int playerIndex;
	EdgeLocation spot1;
	EdgeLocation spot2;
	
	public BuildRoadCardParams(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		this.playerIndex = playerIndex;
		this.spot1 = spot1;
		this.spot2 = spot2;
	}
	public String getType(){
		return type;
	}
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public EdgeLocation getSpot1() {
		return spot1;
	}
	public void setSpot1(EdgeLocation spot1) {
		this.spot1 = spot1;
	}
	public EdgeLocation getSpot2() {
		return spot2;
	}
	public void setSpot2(EdgeLocation spot2) {
		this.spot2 = spot2;
	}
		

}
