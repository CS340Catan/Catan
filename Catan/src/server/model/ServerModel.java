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
	/*
	 * transient to avoid serialization
	 */
	private transient List<ICommand> commands = new ArrayList<>();
	private String initialGameStateJSON = null;

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

	public void validateResources(ResourceList resourceList) {
		if (resourceList.getBrick() < 0) {
			resourceList.setBrick(0);
		}
		if (resourceList.getWheat() < 0) {
			resourceList.setWheat(0);
		}
		if (resourceList.getOre() < 0) {
			resourceList.setOre(0);
		}
		if (resourceList.getWood() < 0) {
			resourceList.setWood(0);
		}
		if (resourceList.getSheep() < 0) {
			resourceList.setSheep(0);
		}
	}

	public void addResourceFromBank(int playerIndex, ResourceType type,
			int amount) {
		if (this.getTurnTracker().getStatus().toUpperCase()
				.equals("FIRSTROUND")
				|| this.getTurnTracker().getStatus().toUpperCase()
						.equals("SECONDROUND")) {
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
		/*
		 * see who has most roads, must be >=5 if there's a tie keep the old
		 * player, else switch to new player
		 */

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

	public void reallocateLargestArmy() {
		/*
		 * See who has most roads, must be >=5 if there's a tie keep the old
		 * player, else switch to new player
		 */
		Player[] players = this.getPlayers();

		for (Player player : players) {
			if (hasLargestArmy(player.getPlayerIndex())) {
				/*
				 * Decrement old largest road player victory points.
				 */
				if (this.getTurnTracker().getLongestRoad() != -1) {
					Player loser = players[this.getTurnTracker()
							.getLargestArmy()];
					loser.setVictoryPoints(loser.getVictoryPoints() - 2);
				}

				/*
				 * Change largest army player and add player's victory points by
				 * two.
				 */
				this.getTurnTracker().setLargestArmy(player.getPlayerIndex());
				player.setVictoryPoints(player.getVictoryPoints() + 2);

			}
		}

	}

	private boolean hasLargestArmy(int playerIndex) {
		Player[] players = this.getPlayers();
		for (Player player : players) {
			/*
			 * If the player being compared has more soldiers, then return
			 * false.
			 */
			if (player.getPlayerIndex() != playerIndex
					&& player.getSoldiers() >= players[playerIndex]
							.getSoldiers()) {
				return false;
			}
		}
		/*
		 * If you as a player had more soldier cards than everyone AND you had
		 * at least 3 soldier cards, then return true.
		 */
		if (players[playerIndex].getSoldiers() >= 3) {
			return true;
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
		for (Player player : this.getPlayers()) {
			/*
			 * If a player has not already discarded half of his cards and he
			 * needs to discard cards (i.e. he has more than 7 cards), then
			 * return true. Else, if no player needs to discard still, then
			 * return false.
			 */
			if (player.alreadyDiscarded() == false
					&& player.getResources().totalResourceCount() > 7) {
				return true;
			}
		}

		return false;
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

	public String getInitialGameStateJSON() {
		return initialGameStateJSON;
	}

	public void setInitialGameStateJSON(String initialGameStateJSON) {
		this.initialGameStateJSON = initialGameStateJSON;
	}
}
