package client.join;

import java.util.Observable;
import java.util.Observer;

import shared.communication.*;
import shared.definitions.CatanColor;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.ServerProxy;
import client.data.*;
import client.misc.*;
import client.model.ClientModel;

/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements
		IJoinGameController, Observer {

	private GameListPoller gameListPoller;
	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private IServer server;
	private GameInfo storeGame;

	/**
	 * JoinGameController constructor
	 * 
	 * @param view
	 *            Join game view
	 * @param newGameView
	 *            New game view
	 * @param selectColorView
	 *            Select color view
	 * @param messageView
	 *            Message view (used to display error messages that occur while
	 *            the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView,
			ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);

		ClientModel.getNotifier().addObserver(this);
		this.server = ServerProxy.getSingleton();
		this.gameListPoller = new GameListPoller(server, this);
	}

	@Override
	public void start() {
		try {
			GameInfo[] gameInfoList = null;
			GameSummary[] gameList = server.getGameList();
			
			if(gameList != null){
				gameInfoList = new GameInfo[gameList.length];
				for (int i = 0; i < gameList.length; i++) {
					gameInfoList[i] = gameList[i].toGameInfo();
				}
			}
			else {
				gameInfoList = new GameInfo[0];
			}

			getJoinGameView().setGames(gameInfoList,
					UserPlayerInfo.getSingleton().toPlayerInfo());

		} catch (ServerResponseException e) {
			String outputStr = "Server Failure.";
			String title = "Invalid server.";

			messageView.setTitle(title);
			messageView.setMessage(outputStr);
			messageView.setController(this);
			messageView.showModal();

			e.printStackTrace();
		}

		getJoinGameView().showModal();
		if (!this.gameListPoller.isTimerRunning()) {
			this.gameListPoller.setTimer();
		}
	}

	@Override
	public void startCreateNewGame() {

		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		try {
			/*
			 * Package the create game parameters to be sent over to the server.
			 * These values can be grabbed from the newGameView.
			 */
			GameSummary[] gameList = server.getGameList();
			for (GameSummary game : gameList) {
				if (game.getTitle().equals(this.newGameView.getTitle())) {
					throw new InvalidInputException("Name already exists!");
				}
			}

			/*
			 * System.out.println("Title:" + this.newGameView.getTitle());
			 * System.out.println("Random Hexes:" +
			 * this.newGameView.getRandomlyPlaceHexes());
			 * System.out.println("Random Numbers:" +
			 * this.newGameView.getRandomlyPlaceNumbers());
			 * System.out.println("Random Ports:" +
			 * this.newGameView.getUseRandomPorts());
			 */

			CreateGameParams createGameParams = new CreateGameParams(
					this.newGameView.getRandomlyPlaceHexes(),
					this.newGameView.getRandomlyPlaceNumbers(),
					this.newGameView.getUseRandomPorts(),
					this.newGameView.getTitle());

			/*
			 * Call the server to create a new game and then close the new game
			 * view.
			 */
			GameInfo gameInfo = server.createGame(createGameParams);
			JoinGameParams params = new JoinGameParams(
					CatanColor.RED.toString(), gameInfo.getId());
			server.joinGame(params);
			getNewGameView().closeModal();
			getNewGameView().setTitle("");

			/*
			 * Re-update the game list after creating the new game. This should
			 * add the newly created game.
			 */
			GameInfo[] gameInfoList = new GameInfo[gameList.length];
			for (int i = 0; i < gameList.length; i++) {
				gameInfoList[i] = gameList[i].toGameInfo();
			}

			getJoinGameView().setGames(gameInfoList,
					UserPlayerInfo.getSingleton().toPlayerInfo());
		} catch (ServerResponseException e) {
			/*
			 * Throw and error if there is an error with the server, i.e. a 400
			 * response is returned from the server.
			 */
			String outputStr = "Server Failure.";
			String title = "Server Failure.";

			messageView.setTitle(title);
			messageView.setMessage(outputStr);
			messageView.setController(this);
			messageView.showModal();

			e.printStackTrace();
		} catch (InvalidInputException e) {
			String outputStr = "Invalid Game Name.";
			String title = "Invalid Game Name.";

			messageView.setTitle(title);
			messageView.setMessage(outputStr);
			messageView.setController(this);
			messageView.showModal();

			e.printStackTrace();
		}
	}

	@Override
	public void startJoinGame(GameInfo game) {
		this.storeGame = game;
		gameListPoller.stopTimer();

		/*
		 * Enable all colors, acting as a reset button.
		 */
		getSelectColorView().setColorEnabled(CatanColor.BLUE, true);
		getSelectColorView().setColorEnabled(CatanColor.BROWN, true);
		getSelectColorView().setColorEnabled(CatanColor.GREEN, true);
		getSelectColorView().setColorEnabled(CatanColor.ORANGE, true);
		getSelectColorView().setColorEnabled(CatanColor.PUCE, true);
		getSelectColorView().setColorEnabled(CatanColor.RED, true);
		getSelectColorView().setColorEnabled(CatanColor.YELLOW, true);
		getSelectColorView().setColorEnabled(CatanColor.PURPLE, true);
		getSelectColorView().setColorEnabled(CatanColor.WHITE, true);

		for (PlayerInfo player : game.getPlayers()) {
			if (!player.getName().equals(
					UserPlayerInfo.getSingleton().getName())) {
				getSelectColorView().setColorEnabled(player.getColor(), false);
			}
		}

		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
		gameListPoller.setTimer();
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		try {
			/*
			 * Initiate poller to start polling once the player has joined the
			 * game.
			 */

			/*
			 * Package the join game parameters to be sent over to the server.
			 * These values can be grabbed from the storedGame id as well as the
			 * Inputed color. These values are then sent over to the server to
			 * join a game.
			 */

			int joinGameID = this.storeGame.getId();
			JoinGameParams joinGameParams = new JoinGameParams(
					color.toString(), joinGameID);
			server.joinGame(joinGameParams); // should return "success" in
												// object

			/*
			 * Close the join game view and the select color view and execute
			 * joinAction, which will open the PlayerWaitingView.
			 */
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();

			UserPlayerInfo.getSingleton().setColor(color);
			// ClientModel.getSingleton().setVersion(-1);
			joinAction.execute();

		} catch (ServerResponseException e) {
			String outputStr = "Server Failure.";
			String title = "Server Failure.";

			messageView.setTitle(title);
			messageView.setMessage(outputStr);
			messageView.setController(this);
			messageView.showModal();

			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// DO NOTHING
	}

	public void updateGameList(GameInfo[] newGameList) {
		// System.out.println("Entered JoinGameController:updateGameList() method");
		getJoinGameView().setGames(newGameList,
				UserPlayerInfo.getSingleton().toPlayerInfo());
	}

	public IJoinGameView getJoinGameView() {

		return (IJoinGameView) super.getView();
	}

	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {

		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value
	 *            The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {

		joinAction = value;
	}

	public INewGameView getNewGameView() {

		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {

		this.newGameView = newGameView;
	}

	public ISelectColorView getSelectColorView() {

		return selectColorView;
	}

	public void setSelectColorView(ISelectColorView selectColorView) {

		this.selectColorView = selectColorView;
	}

	public IMessageView getMessageView() {

		return messageView;
	}

	public void setMessageView(IMessageView messageView) {

		this.messageView = messageView;
	}

}
