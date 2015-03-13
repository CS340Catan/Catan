package server.commands;

import shared.communication.BuildSettlementParams;
import shared.communication.VertexLocationParam;
import shared.locations.VertexLocation;

/**
 * Builds a settlement at the right location
 * @author Seth White
 *
 */
public class BuildSettlmentCommand implements ICommand {
	int playerIndex;
	String type;
	VertexLocationParam vertexLocationParam;
	
	public BuildSettlmentCommand(BuildSettlementParams params){
		this.playerIndex = params.getPlayerIndex();
		this.type = params.getType();
		this.vertexLocationParam = params.getVertexLocation();
	}
	/**
	 * Builds a settlement at the right location
	 * @author Seth White
	 *
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
