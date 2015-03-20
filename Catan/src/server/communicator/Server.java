package server.communicator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.sun.net.httpserver.HttpServer;

import server.facade.ServerFacade;
import server.httpHandlers.*;

public class Server {

	/**
	 * The API for the server
	 */
	// Handler classes
	private HttpServer httpServer;
	private int portNumber = 8081; // default portnumber
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private String host = "localhost";
	
	private static Logger ServerLogger;
	
	static
	{
		Level logLevel = Level.ALL;
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
		ServerFacade.getSingleton().setFirstGame();
	}

	/**
	 * Constructor for cases with specified port number
	 * 
	 * @param portnumber
	 */
	private Server(int portnumber) {
		this.portNumber = portnumber;
//		initialize();
	}

	/**
	 * uses default port 8081
	 */
	private Server() {
//		initialize();
	}

	/**
	 * starts the server
	 */
	private void run() {
		System.out.println("SERVER on port: " + portNumber);
		try {
			httpServer = HttpServer.create(new InetSocketAddress(portNumber),
					MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {
			System.out.println("Error starting HTTP server: " + e.getMessage());
			e.printStackTrace();
		}
		
		//Swagger contexts
		httpServer.createContext("/docs/api/data", new Handlers.JSONAppender("")); 
		httpServer.createContext("/docs/api/view", new Handlers.BasicFile("")); 
		
		httpServer.createContext("/user/login", new LoginHandler());
		httpServer.createContext("/user/register", new RegisterHandler());
		httpServer.createContext("/games/list", new ListGameHandler());
		httpServer.createContext("/games/create", new CreateGameHandler());
		httpServer.createContext("/games/save", new SaveGameHandler());
		httpServer.createContext("/games/load", new LoadGameHandler());
		httpServer.createContext("/game/model", new ModelHandler());
		httpServer.createContext("/game/reset", new ResetGameHandler());
		httpServer.createContext("/game/commmands", new CommandsHandler());//handle get and post
		httpServer.createContext("/game/addAI", new AddAIHandler());
		httpServer.createContext("/game/listAI", new ListAIHandler());
		httpServer.createContext("/moves/sendChat", new SendChatHandler());
		httpServer.createContext("/moves/rollNumber", new RollNumberHandler());
		httpServer.createContext("/moves/robPlayer", new RobPlayerHandler());
		httpServer.createContext("/moves/finishTurn", new FinishTurnHandler());
		httpServer.createContext("/moves/buyDevCard", new BuyDevCardHandler());
		httpServer.createContext("/moves/Year_of_Plenty", new PlayYearOfPlentyCardHandler());
		httpServer.createContext("/moves/Road_Building", new PlayRoadBuildingCardHandler());
		httpServer.createContext("/moves/Soldier", new PlaySoldierCardHandler());
		httpServer.createContext("/moves/Monopoly", new PlayMonopolyCardHandler());
		httpServer.createContext("/moves/Monument", new PlayMonumentCardHandler());
		httpServer.createContext("/moves/buildRoad", new BuildRoadHandler());
		httpServer.createContext("/moves/buildSettlement", new BuildSettlementHandler());
		httpServer.createContext("/moves/buildCity", new BuildCityHandler());
		httpServer.createContext("/moves/offerTrade", new OfferTradeHandler());
		httpServer.createContext("/moves/acceptTrade", new AcceptTradeHandler());
		httpServer.createContext("/moves/maritimeTrade", new MaritimeTradeHandler());
		httpServer.createContext("/moves/discardCards", new DiscardCardsHandler());
		httpServer.createContext("/util/buildSettlement", new BuildSettlementHandler());

		httpServer.start();
	}

}
