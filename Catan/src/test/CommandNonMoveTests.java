package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.commands.ICommand;
import server.facade.IServerFacade;

public class CommandNonMoveTests {
	IServerFacade serverFacade;
	ICommand command;

	@Before
	public void setUp() throws Exception {
		/*
		 * System.out.println("Testing Create Game 1"); ServerModel serverModel
		 * = new ServerModel(); FacadeSwitch.setMockServer(serverModel);
		 * serverFacade = FacadeSwitch.getSingleton(); UserCredentials
		 * userCredentials = new UserCredentials("testies", "testies"); command
		 * = new RegisterCommand(userCredentials); command.execute(); int
		 * playerID = RegisteredPlayers.getSingleton().getPlayerId("testies");
		 * if(playerID == 0){ fail("failed to register"); } CreateGameParams
		 * params = new CreateGameParams(true,true,true,"Testy Game"); command =
		 * new CreateGameCommand(params); command.execute();
		 * System.out.println("Testing Create Game 2"); GameSummary game =
		 * GameList.getSingleton().getGame("Testy Game"); assertNotNull(game);
		 * int gameID = game.getId(); serverFacade.setGameID(gameID);
		 * serverModel = serverFacade.getServerModel();
		 */
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void RandomTest() {
		fail("Not yet implemented");
	}
}
