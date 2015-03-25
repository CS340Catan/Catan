package server.model;

import java.util.ArrayList;
import java.util.List;

import client.model.ClientModel;
import server.commands.ICommand;
import shared.definitions.ResourceType;
import shared.model.*;
import shared.utils.Serializer;

/**
 * Contains all data relevant to the game
 * 
 * <pre>
 * <b>Domain:</b>
 * -bank:ResourceList
 * -chat:MessageList
 * -log:MessageList
 * -map:Map
 * -players:[Player]
 * -tradeOffer:TradeOffer
 * -turnTracker:TurnTracker
 * -version:int
 * -winner:int
 * </pre>
 * 
 * @author Seth White
 * 
 */
public class ServerModel extends AbstractModel {
	private int gameID;
	private List<ICommand> commands = new ArrayList<>();
	private boolean[] needToDiscard = new boolean[4];

	public ServerModel() {
		this.setMap(new Map(new Hex[0], new Port[0], new Road[0],
				new VertexObject[0], new VertexObject[0], gameID, null));
		this.setBank(new ResourceList(19, 19, 19, 19, 19));
		this.setDeck(new Deck(2, 5, 14, 2, 2));
		this.setTurnTracker(new TurnTracker(0, "FirstRound", -1, -1));
		this.setTradeOffer(new TradeOffer(-1, -1, new ResourceList(0, 0, 0, 0,
				0)));
		this.setChat(new MessageList(new MessageLine[0]));
		this.setCommands(new ArrayList<ICommand>());
		this.setLog(new MessageList(new MessageLine[0]));
		this.setVersion(0);
	}

	public ClientModel toClientModel() {
		// AbstractModel am = (AbstractModel) this;
		// ClientModel cm = (ClientModel) am;
		String jsonString = Serializer.serializeServerModel(this);
		ClientModel cm = Serializer.deserializeClientModel(jsonString);
		return cm;
	}
	public void validateResources(ResourceList resourceList){
		if(resourceList.getBrick() < 0){
			resourceList.setBrick(0);
		}
		if(resourceList.getWheat() < 0){
			resourceList.setWheat(0);
		}
		if(resourceList.getOre() < 0){
			resourceList.setOre(0);
		}
		if(resourceList.getWood() < 0){
			resourceList.setWood(0);
		}
		if(resourceList.getSheep() < 0){
			resourceList.setSheep(0);
		}
	}
	public void addResourceFromBank(int playerIndex, ResourceType type,
			int amount) {
		if(this.getTurnTracker().getStatus().toUpperCase().equals("FIRSTROUND") || this.getTurnTracker().getStatus().toUpperCase().equals("SECONDROUND")){
			return;
		}
		ResourceList playerResources = this.getPlayers()[playerIndex]
				.getResources();
		this.validateResources(playerResources);		
		ResourceList bankResources = this.getBank();

		switch (type) {
		case BRICK:
			playerResources.setBrick(playerResources.getBrick() + amount);
			bankResources.setBrick(bankResources.getBrick() - amount);
			break;
		case ORE:
			playerResources.setOre(playerResources.getOre() + amount);
			bankResources.setOre(bankResources.getOre() - amount);
			break;
		case SHEEP:
			playerResources.setSheep(playerResources.getSheep() + amount);
			bankResources.setSheep(bankResources.getSheep() - amount);
			break;
		case WHEAT:
			playerResources.setWheat(playerResources.getWheat() + amount);
			bankResources.setWheat(bankResources.getWheat() - amount);
			break;
		case WOOD:
			playerResources.setWood(playerResources.getWood() + amount);
			bankResources.setWood(bankResources.getWood() - amount);
			break;
		default:
			break;
		}

	}

	public void addRoad(Road road) {

		Road[] roads = this.getMap().getRoads();

		// copy old array into slightly bigger new array
		Road[] newRoads = new Road[roads.length + 1];
		for (int i = 0; i < roads.length; i++) {
			newRoads[i] = roads[i];
		}
		// add new road
		newRoads[newRoads.length - 1] = road;

		this.getMap().setRoads(newRoads);
	}

	public void reallocateLongestRoad() {
		// see who has most roads, must be >=5 if there's a tie keep the old
		// player, else switch to new player

		Player[] players = this.getPlayers();

		// iterate
		for (Player player : players) {
			if (hasLongestRoad(player.getPlayerIndex())) {

				// decrement old longest road player victory points
				if (this.getTurnTracker().getLongestRoad() != -1) {
					Player loser = players[this.getTurnTracker()
							.getLongestRoad()];
					loser.setVictoryPoints(loser.getVictoryPoints() - 2);
				}

				// change longest road player
				this.getTurnTracker().setLongestRoad(player.getPlayerIndex());

				// increment victory points for new longest road player
				player.setVictoryPoints(player.getVictoryPoints() + 2);

			}
			// special case: 2 people tie and surpass 5 at the same time
			else if (hasTiedLongestRoad(player.getPlayerIndex())
					&& (this.getTurnTracker().getLongestRoad() == -1)) { // initialized
																			// to
																			// -1
																			// right?
				this.getTurnTracker().setLongestRoad(player.getPlayerIndex());
				player.setVictoryPoints(player.getVictoryPoints() + 2);
			}
		}

	}

	private boolean hasLongestRoad(int playerIndex) {

		Player[] players = this.getPlayers();

		for (Player player : players) {
			if (player.getPlayerIndex() != playerIndex
					&& player.getNumberRoadsBuilt() >= players[playerIndex]
							.getNumberRoadsBuilt()) {
				return false;
			}
		}

		if (players[playerIndex].getNumberRoadsBuilt() >= 5) {
			return true;
		} else {
			return false;
		}
	}

	private boolean hasTiedLongestRoad(int playerIndex) {

		Player[] players = this.getPlayers();

		for (Player player : players) {
			if (player.getPlayerIndex() != playerIndex
					&& players[playerIndex].getNumberRoadsBuilt() >= 5
					&& player.getNumberRoadsBuilt() == players[playerIndex]
							.getNumberRoadsBuilt()) {
				return true;
			}
		}

		return false;
	}

	public void addSettlement(VertexObject settlement) {

		VertexObject[] settlements = this.getMap().getSettlements();

		// copy old array into slightly bigger new array
		VertexObject[] newSettlements = new VertexObject[settlements.length + 1];
		for (int i = 0; i < settlements.length; i++) {
			newSettlements[i] = settlements[i];
		}
		// add new settlement
		newSettlements[newSettlements.length - 1] = settlement;

		this.getMap().setSettlements(newSettlements);
	}

	public void addCity(VertexObject city) {
		VertexObject[] currentCities = this.getMap().getCities();
		int currentCount = currentCities.length;
		VertexObject[] newCityList = new VertexObject[currentCount + 1];
		for (int i = 0; i < currentCount; i++) {
			newCityList[i] = currentCities[i];
		}
		newCityList[currentCount] = city;
		this.getMap().setCities(newCityList);

		// remove old settlement
		this.removeSettlementForCity(city);
	}

	public boolean needToDiscard() {
		for (boolean discard : this.needToDiscard) {
			if (discard == true) {
				return true;
			}
		}

		return false;
	}

	public void initializeNeedToDiscard() {
		Player[] players = this.getPlayers();

		for (int i = 0; i < players.length; i++) {
			if (players[i].getResources().count() >= 7) {
				this.needToDiscard[i] = true;
			} else {
				this.needToDiscard[i] = false;
			}
		}
	}

	public void setNeedToDiscard(boolean discard, int playerIndex) {
		this.needToDiscard[playerIndex] = discard;
	}

	private void removeSettlementForCity(VertexObject city) {

		for (VertexObject settlement : this.getMap().getSettlements()) {
			if (settlement.isEquivalent(city)) {

				VertexObject[] oldSettlements = this.getMap().getSettlements();
				VertexObject[] newSettlements = new VertexObject[oldSettlements.length - 1];

				int i = 0;
				for (VertexObject s : oldSettlements) {
					if (s.equals(settlement)) {
						continue;
					} else {
						newSettlements[i] = s;
						i++;
					}
				}

				this.getMap().setSettlements(newSettlements);
				return;
			}
		}
	}

	public String toString() {
		return Serializer.serializeServerModel(this);
	}

	public void incrementVersion() {
		this.setVersion(this.getVersion() + 1);
	}

	public List<ICommand> getCommands() {
		return commands;
	}

	public void setCommands(List<ICommand> modelCommands) {
		this.commands = modelCommands;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int ID) {
		this.gameID = ID;
	}
}
