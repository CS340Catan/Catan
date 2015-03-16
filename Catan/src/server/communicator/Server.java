package server.communicator;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import server.facade.ServerFacade;
import server.httpHandlers.AcceptTradeHandler;
import server.httpHandlers.AddAIHandler;
import server.httpHandlers.BuildCityHandler;
import server.httpHandlers.BuildRoadHandler;
import server.httpHandlers.BuildSettlementHandler;
import server.httpHandlers.BuyDevCardHandler;
import server.httpHandlers.CommandsHandler;
import server.httpHandlers.CreateGameHandler;
import server.httpHandlers.DiscardCardsHandler;
import server.httpHandlers.FinishTurnHandler;
import server.httpHandlers.ListAIHandler;
import server.httpHandlers.ListGameHandler;
import server.httpHandlers.LoadGameHandler;
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

public class Server {

	/**
	 * The API for the server
	 */
	// Handler classes
	private HttpServer httpServer;
	private int portNumber = 8081; // default portnumber
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private String host = "localhost";

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
