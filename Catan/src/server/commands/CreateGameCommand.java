package server.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import server.facade.ServerFacade;
import server.model.GameList;
import server.model.ServerModel;
import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.communication.PlayerSummary;
import shared.definitions.PortType;
import shared.locations.HexLocation;
import shared.model.Hex;
import shared.model.Port;
import shared.utils.IDGenerator;

/**
 * This command creates a new game and adds it to the list of games.
 * 
 * @author winstonhurst
 */
public class CreateGameCommand extends ICommand {
	private CreateGameParams params;
	private ServerModel serverModel;

	/**
	 * 
	 * @param params
	 *            - contains randomTiles (bool) randomNumbers (bool), and the
	 *            game's name
	 */
	public CreateGameCommand(CreateGameParams params) {
		this.params = params;
		serverModel = new ServerModel();
		this.setType("CreateGame");

	}

	/**
	 * Generates a new game id Adds new game to the game list. Adds a new key =>
	 * pair to the game map (id=>this new game)
	 */
	@Override
	public void execute() {
		int gameID = IDGenerator.generateGameID();
		PlayerSummary[] summaries = new PlayerSummary[4];
		summaries[0] = null;
		summaries[1] = null;
		summaries[2] = null;
		summaries[3] = null;
		
		GameSummary gameSummary = new GameSummary(params.getname(), gameID, null);
		GameList.getSingleton().addGame(gameSummary);
		serverModel = new ServerModel();
		this.addPorts();
		this.addHexes();
		ServerFacade.getSingleton().getModelMap().put(gameID, serverModel);
	}

	private void addPorts() {
		PortType portType = PortType.BRICK;
		String[] resources = new String[9];
		resources[0] = "three";
		resources[1] = "wheat";
		resources[2] = "ore";
		resources[3] = "three";
		resources[4] = "sheep";
		resources[5] = "three";
		resources[6] = "brick";
		resources[7] = "wood";
		resources[8] = "three";
		
		if(params.isRandomPorts()){
			Object[] objectArray = this.randomize(resources);
			resources = Arrays.copyOf(objectArray, objectArray.length, String[].class);			
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

		// getView().placeRobber(new HexLocation(0, 0));
		//
		// getView().addNumber(new HexLocation(-2, 0), 2);
		// getView().addNumber(new HexLocation(-2, 1), 3);
		// getView().addNumber(new HexLocation(-2, 2), 4);
		// getView().addNumber(new HexLocation(-1, 0), 5);
		// getView().addNumber(new HexLocation(-1, 1), 6);
		// getView().addNumber(new HexLocation(1, -1), 8);
		// getView().addNumber(new HexLocation(1, 0), 9);
		// getView().addNumber(new HexLocation(2, -2), 10);
		// getView().addNumber(new HexLocation(2, -1), 11);
		// getView().addNumber(new HexLocation(2, 0), 12);

	}

	public Object[] randomize(Object[] resources) {
		Random rand = new Random();
		Object[] tempArray = new Object[resources.length];
		int j = 0;
		for(int i = resources.length-1; i >= 0; i--){
			int randomNum = rand.nextInt((resources.length));
			while(tempArray[randomNum] != null){
				randomNum = rand.nextInt((resources.length));
			}
			tempArray[randomNum] = resources[i];
		}
		return tempArray;
	}

	public void addHexes() {
		// getView().placeRobber(new HexLocation(0, 0));
		//
		Integer[] numbers = new Integer[18];
		numbers[0] = 5;
		numbers[1] = 2;
		numbers[2] = 6;
		numbers[3] = 8;
		numbers[4] = 10;
		numbers[5] = 9;
		numbers[6] = 3;
		numbers[7] = 3;
		numbers[8] = 11;
		numbers[9] = 4;
		numbers[10] = 8;
		numbers[11] = 4;
		numbers[12] = 9;
		numbers[13] = 5;
		numbers[14] = 10;
		numbers[15] = 11;
		numbers[16] = 12;
		numbers[17] = 6;
		if(params.isRandomNumbers()){
			Object[] objectArray = (Integer[]) this.randomize(numbers);
			numbers = Arrays.copyOf(objectArray, objectArray.length, Integer[].class);			
		}
		HexLocation[] locations = new HexLocation[19];
		locations[0] = new HexLocation(-2, 0);
		locations[1] = new HexLocation(-2, 1);
		locations[2] = new HexLocation(-2, 2);
		locations[3] = new HexLocation(-1, -1);
		locations[4] = new HexLocation(-1, 0);
		locations[5] = new HexLocation(-1, 1);
		locations[6] = new HexLocation(-1, 2);
		locations[7] = new HexLocation(0, -2);// default robber
		locations[8] = new HexLocation(0, -1);
		locations[9] = new HexLocation(0, 0);
		locations[10] = new HexLocation(0, 1);
		locations[11] = new HexLocation(0, 2);
		locations[12] = new HexLocation(1, -2);
		locations[13] = new HexLocation(1, -1);
		locations[14] = new HexLocation(1, 0);
		locations[15] = new HexLocation(1, 1);
		locations[16] = new HexLocation(2, -2);
		locations[17] = new HexLocation(2, -1);
		locations[18] = new HexLocation(2, 0);

		String[] resources = new String[19];
		resources[0] = "ore";
		resources[1] = "wheat";
		resources[2] = "wood";
		resources[3] = "brick";
		resources[4] = "sheep";
		resources[5] = "sheep";
		resources[6] = "ore";
		resources[7] = "desert";
		resources[8] = "wood";
		resources[9] = "wheat";
		resources[10] = "wood";
		resources[11] = "wheat";
		resources[12] = "brick";
		resources[13] = "ore";
		resources[14] = "brick";
		resources[15] = "sheep";
		resources[16] = "wood";
		resources[17] = "sheep";
		resources[18] = "wheat";
		
		if(params.isRandomTiles()){
			Object[] objectArray = this.randomize(resources);
			resources = Arrays.copyOf(objectArray, objectArray.length, String[].class);	
		}

		Hex[] hexes = new Hex[19];
		hexes[0] = new Hex(locations[0], resources[0], numbers[0]);
		hexes[1] = new Hex(locations[1], resources[1], numbers[1]);
		hexes[2] = new Hex(locations[2], resources[2], numbers[2]);
		hexes[3] = new Hex(locations[3], resources[3], numbers[3]);
		hexes[4] = new Hex(locations[4], resources[4], numbers[4]);
		hexes[5] = new Hex(locations[5], resources[5], numbers[5]);
		hexes[6] = new Hex(locations[6], resources[6], numbers[6]);
		hexes[7] = new Hex(locations[7], resources[7], -1);// default robber
		hexes[8] = new Hex(locations[8], resources[8], numbers[7]);
		hexes[9] = new Hex(locations[9], resources[9], numbers[8]);
		hexes[10] = new Hex(locations[10], resources[10], numbers[9]);
		hexes[11] = new Hex(locations[11], resources[11], numbers[10]);
		hexes[12] = new Hex(locations[12], resources[12], numbers[11]);
		hexes[13] = new Hex(locations[13], resources[13], numbers[12]);
		hexes[14] = new Hex(locations[14], resources[14], numbers[13]);
		hexes[15] = new Hex(locations[15], resources[15], numbers[14]);
		hexes[16] = new Hex(locations[16], resources[16], numbers[15]);
		hexes[17] = new Hex(locations[17], resources[17], numbers[16]);
		hexes[18] = new Hex(locations[18], resources[18], numbers[17]);
			for(Hex hex : hexes){
				if(hex.getResource().equals("desert")){
					serverModel.getMap().setRobber(hex.getLocation());
				}
			}
		serverModel.getMap().setHexes(hexes);
	}
}
