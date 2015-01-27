package shared.communication;

import shared.locations.VertexLocation;
/**
 * Class which contains data for 'moves/buildCity'
 * @author winstonhurst
 *
 */
public class BuildCityParams {

		String type = "buildCity";
		/**
		 * Who's placing the city,
		 */
		int playerIndex;
		VertexLocation vertexLocation;
		
		public BuildCityParams(int playerIndex, VertexLocation vertexLocation) {
			this.playerIndex = playerIndex;
			this.vertexLocation = vertexLocation;
		}
		
		public int getPlayerIndex() {
			return playerIndex;
		}
		public void setPlayerIndex(int playerIndex) {
			this.playerIndex = playerIndex;
		}
		public VertexLocation getVertexLocation() {
			return vertexLocation;
		}
		public void setVertexLocation(VertexLocation vertexLocation) {
			this.vertexLocation = vertexLocation;
		}
		public String getType() {
			return type;
		}
		
		
}
