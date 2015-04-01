package server.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import server.facade.FacadeSwitch;
import server.httpHandlers.AcceptTradeHandler;
import server.httpHandlers.BuildCityHandler;
import server.httpHandlers.BuildRoadHandler;
import server.httpHandlers.BuildSettlementHandler;
import server.httpHandlers.BuyDevCardHandler;
import server.httpHandlers.CommandsHandler;
import server.httpHandlers.CreateGameHandler;
import server.httpHandlers.DiscardCardsHandler;
import server.httpHandlers.FinishTurnHandler;
import server.httpHandlers.Handlers;
import server.httpHandlers.JoinGameHandler;
import server.httpHandlers.ListGameHandler;
import server.httpHandlers.LoadGameHandler;
import server.httpHandlers.LogLevelHandler;
import server.httpHandlers.LoginHandler;
import server.httpHandlers.MaritimeTradeHandler;
import server.httpHandlers.ModelHandler;
import server.httpHandlers.OfferTradeHandler;
import server.httpHandlers.PlayMonopolyCardHandler;
import server.httpHandlers.PlayMonumentCardHandler;
import server.httpHandlers.PlayRoadBuildingCardHandler;
import server.httpHandlers.PlaySoldierCardHandler;
import server.httpHandlers.PlayYearOfPlentyCardHandler;
import server.httpHandlers.RegisterHandler;
import server.httpHandlers.ResetGameHandler;
import server.httpHandlers.RobPlayerHandler;
import server.httpHandlers.RollNumberHandler;
import server.httpHandlers.SaveGameHandler;
import server.httpHandlers.SendChatHandler;
import server.model.ServerModel;
import shared.utils.Serializer;

import com.sun.net.httpserver.HttpServer;

public class Server {

	/**
	 * The API for the server
	 */
	// Handler classes
	private static String clientModelJson = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-2},\"number\":3},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"brick\",\"location\":{\"x\":0,\"y\":-1},\"number\":8},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-1},\"number\":11},{\"resource\":\"sheep\",\"location\":{\"x\":-2,\"y\":0},\"number\":10},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":12},{\"resource\":\"sheep\",\"location\":{\"x\":0,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":1,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":2,\"y\":0},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":1},\"number\":6},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":0,\"y\":1},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":2},\"number\":9},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":2},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":2}],\"roads\":[{\"owner\":3,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":0}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":12,\"playerIndex\":0,\"name\":\"string\",\"color\":\"purple\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":13,\"playerIndex\":1,\"name\":\"test1\",\"color\":\"puce\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":14,\"playerIndex\":2,\"name\":\"test2\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":12,\"cities\":4,\"settlements\":4,\"soldiers\":0,\"victoryPoints\":1,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":15,\"playerIndex\":3,\"name\":\"test3\",\"color\":\"orange\"}],\"log\":{\"lines\":[{\"source\":\"string\",\"message\":\"string built a settlement\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":24,\"wood\":24,\"sheep\":24,\"wheat\":24,\"ore\":24},\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":4}";

	private HttpServer httpServer;
	private int portNumber = 8081; // default portnumber
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private String host = "localhost";

	private static Logger ServerLogger;

	static {
		Level logLevel = Level.OFF;
		//Level logLevel = Level.OFF;
		ServerLogger = Logger.getLogger("CatanServer");
		ServerLogger.setLevel(logLevel);
		ServerLogger.setUseParentHandlers(false);
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		ServerLogger.addHandler(consoleHandler);
	}

	/**
	 * starts the server with port number 8081
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			new Server(Integer.parseInt(args[0])).run();
		} else {
			new Server().run();
		}
		if(args.length > 1) {
			if(args[1].equals("-mock")) {
				ServerModel model = (ServerModel) Serializer.deserialize(clientModelJson, ServerModel.class);
				FacadeSwitch.setMockServer(model);
				System.out.println("Mock Server Running");
			}
		}
		else {
			FacadeSwitch.getSingleton().setFirstGame();
		}
	}

	/**
	 * Constructor for cases with specified port number
	 * 
	 * @param portnumber
	 */
	private Server(int portnumber) {
		this.portNumber = portnumber;
		// initialize();
	}

	/**
	 * uses default port 8081
	 */
	private Server() {
		// initialize();
	}

	/**
	 * starts the server
	 */
	private void run() {
		
		try {
			httpServer = HttpServer.create(new InetSocketAddress(portNumber),
					MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		// Swagger contexts
		httpServer.createContext("/docs/api/data",
				new Handlers.JSONAppender(""));
		httpServer.createContext("/docs/api/view", new Handlers.BasicFile(""));

		httpServer.createContext("/user/login", new LoginHandler());
		httpServer.createContext("/user/register", new RegisterHandler());
		httpServer.createContext("/games/list", new ListGameHandler());
		httpServer.createContext("/games/create", new CreateGameHandler());
		httpServer.createContext("/games/join", new JoinGameHandler());
		httpServer.createContext("/games/save", new SaveGameHandler());
		httpServer.createContext("/games/load", new LoadGameHandler());
		httpServer.createContext("/game/model", new ModelHandler());
		httpServer.createContext("/game/reset", new ResetGameHandler());
		httpServer.createContext("/game/commands", new CommandsHandler());
		// httpServer.createContext("/game/addAI", new AddAIHandler());
		// httpServer.createContext("/game/listAI", new ListAIHandler());
		httpServer.createContext("/moves/sendChat", new SendChatHandler());
		httpServer.createContext("/moves/rollNumber", new RollNumberHandler());
		httpServer.createContext("/moves/robPlayer", new RobPlayerHandler());
		httpServer.createContext("/moves/finishTurn", new FinishTurnHandler());
		httpServer.createContext("/moves/buyDevCard", new BuyDevCardHandler());
		httpServer.createContext("/moves/Year_of_Plenty",
				new PlayYearOfPlentyCardHandler());
		httpServer.createContext("/moves/Road_Building",
				new PlayRoadBuildingCardHandler());
		httpServer
				.createContext("/moves/Soldier", new PlaySoldierCardHandler());
		httpServer.createContext("/moves/Monopoly",
				new PlayMonopolyCardHandler());
		httpServer.createContext("/moves/Monument",
				new PlayMonumentCardHandler());
		httpServer.createContext("/moves/buildRoad", new BuildRoadHandler());
		httpServer.createContext("/moves/buildSettlement",
				new BuildSettlementHandler());
		httpServer.createContext("/moves/buildCity", new BuildCityHandler());
		httpServer.createContext("/moves/offerTrade", new OfferTradeHandler());
		httpServer
				.createContext("/moves/acceptTrade", new AcceptTradeHandler());
		httpServer.createContext("/moves/maritimeTrade",
				new MaritimeTradeHandler());
		httpServer.createContext("/moves/discardCards",
				new DiscardCardsHandler());
		httpServer.createContext("/util/changeLogLevel",
				new LogLevelHandler());

		httpServer.start();
	}

}
