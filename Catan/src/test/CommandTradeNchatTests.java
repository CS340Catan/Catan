package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.commands.CreateGameCommand;
import server.commands.ICommand;
import server.commands.RegisterCommand;
import server.facade.FacadeSwitch;
import server.facade.IServerFacade;
import server.model.GameList;
import server.model.RegisteredPlayers;
import server.model.ServerModel;
import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.communication.UserCredentials;

public class CommandTradeNchatTests {
	IServerFacade serverFacade;
	ICommand command;
	@Before
	public void setUp() throws Exception {
		ServerModel serverModel = new ServerModel();
		FacadeSwitch.setMockServer(serverModel.toClientModel());
		serverFacade = FacadeSwitch.getSingleton();
		UserCredentials userCredentials = new UserCredentials("testies", "testies");
		command = new RegisterCommand(userCredentials);
		command.execute();
		int playerID = RegisteredPlayers.getSingleton().getPlayerId("testies");
		if(playerID == 0){
			fail("failed to register");
		}
		CreateGameParams params = new CreateGameParams(true,true,true,"Testy Game");
		command = new CreateGameCommand(params);
		command.execute();
		GameSummary game = GameList.getSingleton().getGame("Testy Game");
		assertNotNull(game);
		int gameID = game.getId();
		serverFacade.setGameID(gameID);
		serverModel = serverFacade.getServerModel();
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void AcceptTradeTest() {
		fail("Not yet implemented");
	}
}
