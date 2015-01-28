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
		EdgeLocation roadLocation;
		/**
		 * Whether this is placed for free (setup)
		 */
		boolean free;
		
		public BuildRoadParams(int playerIndex, EdgeLocation roadLocation, boolean free) {
			this.playerIndex = playerIndex;
			this.roadLocation = roadLocation;
			this.free = free;
		}

		public int getPlayerIndex() {
			return playerIndex;
		}

		public void setPlayerIndex(int playerIndex) {
			this.playerIndex = playerIndex;
		}

		public EdgeLocation getRoadLocation() {
			return roadLocation;
		}

		public void setRoadLocation(EdgeLocation roadLocation) {
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
