package shared.communication;

import shared.locations.EdgeLocation;

import com.google.gson.annotations.Expose;
/**
 * Class which contains data for 'moves/buildCity'
 * @author winstonhurst
 *
 */
public class BuildRoadParams {

		@Expose String type = "buildRoad";
		/**
		 * Who's placing the road
		 */
		@Expose int playerIndex; 
		@Expose EdgeLocation roadLocation;
		/**
		 * Whether this is placed for free (setup)
		 */
		@Expose boolean free;
		
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
