package client.join;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.communication.InvalidInputException;
import shared.communication.JoinGameParams;
import shared.definitions.CatanColor;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.data.*;
import client.misc.*;
import client.model.ClientModel;

/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements
		IJoinGameController, Observer {

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

		ClientModel.getSingleton().addObserver(this);
		this.server = ServerProxy.getSingleton();
		/*
		 * TODO how does the server proxy / HTTP communicator take into account
		 * port, host, etc.
		 */
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

	@Override
	public void start() {
		try {
			GameInfo[] gameList = server.getGameList();			
			getJoinGameView().setGames(gameList, PlayerInfo.getSingleton());

			/*
			 * GameInfo[] testGameList = new GameInfo[2]; GameInfo test = new
			 * GameInfo(); test.setId(1); test.setTitle("New Game");
			 * test.addPlayer(new PlayerInfo()); testGameList[0] = test;
			 * GameInfo test2 = new GameInfo(); test2.setId(2);
			 * test2.setTitle("New Game"); test2.addPlayer(new PlayerInfo());
			 * testGameList[1] = test2; getJoinGameView().setGames(testgameList,
			 * null);
			 */

		} catch (ServerResponseException e) {
			e.printStackTrace();
		}

		getJoinGameView().showModal();
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
			CreateGameParams createGameParams = new CreateGameParams(
					this.newGameView.getRandomlyPlaceHexes(),
					this.newGameView.getRandomlyPlaceNumbers(),
					this.newGameView.getUseRandomPorts(),
					this.newGameView.getTitle());

			GameInfo newGameSummary = server.createGame(createGameParams);
			getNewGameView().closeModal();

		} catch (ServerResponseException e) {
			String outputStr = "Could not reach the server.";
			JOptionPane.showMessageDialog(null, outputStr,
					"Server unavailable", JOptionPane.ERROR_MESSAGE);

			e.printStackTrace();
		} catch (InvalidInputException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Server unavailable", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void startJoinGame(GameInfo game) {
		this.storeGame = game;

		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {

		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		// If join succeeded
		try {
			int joinGameID = this.storeGame.getId();

			JoinGameParams joinGameParams = new JoinGameParams(
					color.toString(), joinGameID);
			server.joinGame(joinGameParams);

			getSelectColorView().closeModal();
			getJoinGameView().closeModal();
			// TODO: store returned player index in PlayerInfo
			PlayerInfo.getSingleton().setId(0);// TODO change so that the player
												// index is updated in
												// playerinfo
			joinAction.execute();
		} catch (ServerResponseException e) {
			String outputStr = "Could not reach the server.";
			JOptionPane.showMessageDialog(null, outputStr,
					"Server unavailable", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
