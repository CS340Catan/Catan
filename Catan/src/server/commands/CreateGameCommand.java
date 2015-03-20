package server.commands;

import java.util.Random;

import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.definitions.PortType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.GameList;
import shared.model.Port;
import shared.utils.IDGenerator;

/**
 * 
 * @author winstonhurst
 *This command creates a new game and adds it to the list of games.
 */
public class CreateGameCommand implements ICommand {
	private CreateGameParams params;
    private Random random = new Random();
    private int sheepTileCount = 4;
    private int wheatTileCount = 4;
    private int oreTileCount = 3;
    private int woodTileCount = 4;
    private int brickTileCount = 3;
    private int desertCount = 1;
    private ServerModel serverModel;
	/**
	 * 
	 * @param params - contains randomTiles (bool) randomNumbers (bool), and the game's name 
	 */
	public CreateGameCommand(CreateGameParams params){
		this.params = params;
		serverModel = new ServerModel();
	}
	
	/**
	 * Generates a new game id
	 * Adds new game to the game list.
	 * Adds a new key => pair to the game map (id=>this new game)
	 */
	@Override
	public void execute() {
		int gameID = IDGenerator.generateGameID();
		GameSummary gameSummary = new GameSummary(params.getname(), gameID, null);
		GameList.getSingleton().addGame(gameSummary);
		serverModel = new ServerModel();
		
	}
	
	private void addPorts(){
		PortType portType = PortType.BRICK;
		String[] resources = new String[9];
		resources[0] = "three";
		resources[1] = "wheat";
		resources[2]= "ore";
		resources[3] = "three";
		resources[4] = "sheep";
		resources[5] = "three";
		resources[6] = "brick";
		resources[7] = "wood";
		resources[8] = "three";
		
		if(params.isRandomPorts()){
			this.randomizeResources(resources);
		}
		Port[] ports = new Port[9];
		ports[0] = new Port(resources[0], new HexLocation(0, 3), "N", 3);
		ports[1] = new Port(resources[1], new HexLocation(-1, -2), "S", 2);
		ports[2] = new Port(resources[2], new HexLocation(1, -3), "S", 2);
		ports[3] = new Port(resources[3], new HexLocation(3, -3), "SW", 3);
		ports[4] = new Port(resources[4], new HexLocation(3, -1), "NW", 2);
		ports[5] = new Port(resources[5], new HexLocation(2, 1), "NW", 3);
		ports[6] = new Port(resources[6], new HexLocation(-2, 3), "NE", 2);
		ports[7] = new Port(resources[7], new HexLocation(-3, 2), "NE", 2);
		ports[8] = new Port(resources[8], new HexLocation(-3, 0), "SE", 3);
		serverModel.getMap().setPorts(ports);
	
		
//		getView().placeRobber(new HexLocation(0, 0));
//		
//		getView().addNumber(new HexLocation(-2, 0), 2);
//		getView().addNumber(new HexLocation(-2, 1), 3);
//		getView().addNumber(new HexLocation(-2, 2), 4);
//		getView().addNumber(new HexLocation(-1, 0), 5);
//		getView().addNumber(new HexLocation(-1, 1), 6);
//		getView().addNumber(new HexLocation(1, -1), 8);
//		getView().addNumber(new HexLocation(1, 0), 9);
//		getView().addNumber(new HexLocation(2, -2), 10);
//		getView().addNumber(new HexLocation(2, -1), 11);
//		getView().addNumber(new HexLocation(2, 0), 12);
		
	}
	public String[] randomizeResources(String[] resources){
		Random rand = new Random();
		String[] tempArray = new String[resources.length];
		int j = 0;
		for(int i = resources.length-1; i >= 0; i--){
			int randomNum = rand.nextInt((resources.length - j) );
			tempArray[randomNum] = resources[i];
			j++;
		}
		return tempArray;
	}

	

}
