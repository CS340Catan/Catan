package shared.communication;

import shared.locations.HexLocation;
/**
 * Class which contains data for 'moves/moveSoldier'
 * @author winstonhurst
 *
 */
public class MoveSoldierParams {

		String  type = "Soldier";
		/**
		 * Who's playing this dev card,
		 */
		int playerIndex;
		/**
		 *  The index of the player to rob,
		 */
		int victimIndex;
		/**
		 *  the new location of the robber
		 */
		HexLocation location;
		public MoveSoldierParams(int playerIndex, int victimIndex, HexLocation location) {
			this.playerIndex = playerIndex;
			this.victimIndex = victimIndex;
			this.location = location;
		}
		public int getPlayerIndex() {
			return playerIndex;
		}
		public void setPlayerIndex(int playerIndex) {
			this.playerIndex = playerIndex;
		}
		public int getVictimIndex() {
			return victimIndex;
		}
		public void setVictimIndex(int victimIndex) {
			this.victimIndex = victimIndex;
		}
		public HexLocation getLocation() {
			return location;
		}
		public void setLocation(HexLocation location) {
			this.location = location;
		}
		public String getType() {
			return type;
		}	
}
