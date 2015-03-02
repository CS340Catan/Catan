package shared.communication;

import shared.locations.EdgeLocation;

/**
 * Class which contains data for 'moves/buildCity'
 * @author winstonhurst
 *
 */
public class BuildRoadParams {

		String type = "buildRoad";
		/**
		 * Who's placing the road
		 */
		int playerIndex; 
		EdgeLocationParam roadLocation;
		/**
		 * Whether this is placed for free (setup)
		 */
		boolean free;
		
		public BuildRoadParams(int playerIndex, EdgeLocation roadLocation, boolean free) {
			roadLocation.convertToPrimitives();
			this.playerIndex = playerIndex;
			this.roadLocation.setDirection(roadLocation.getDirection());
			this.roadLocation.setX(roadLocation.getX());
			this.roadLocation.setY(roadLocation.getY());
			this.free = free;
		}

		public int getPlayerIndex() {
			return playerIndex;
		}

		public void setPlayerIndex(int playerIndex) {
			this.playerIndex = playerIndex;
		}

		public EdgeLocationParam getRoadLocation() {
			return roadLocation;
		}

		public void setRoadLocation(EdgeLocationParam roadLocation) {
			this.roadLocation = roadLocation;
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
