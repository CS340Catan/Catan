package server.commands;

import server.facade.FacadeSwitch;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.UserActionParams;
import shared.model.DevCardList;
import shared.model.MessageLine;
import shared.model.Player;
import shared.utils.ServerResponseException;

/**
 * This command finishes a player's turn
 * 
 * @author winstonhurst
 */
public class FinishTurnCommand extends ICommand {
	int playerIndex;
	int gameID;

	/**
	 * 
	 * @param params
	 *            - contains the player's index in the game
	 * @param gameID
	 *            - id of the game on which to act
	 */
	public FinishTurnCommand(UserActionParams params, int gameID) {

		this.playerIndex = params.getPlayerIndex();
		this.gameID = gameID;
		this.setType("FinishTurn");

	}

	/**
	 * Changes current player turn to next player's id. Changes the status of
	 * the game associated with the gameID to Rolling.
	 * 
	 * @throws ServerResponseException
	 *             Thrown if is not the player's turn, therefore, they should
	 *             not be able to end their turn.
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = FacadeSwitch.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		if (controller.canFinishTurn(this.playerIndex)) {
			Player player = model.getPlayers()[playerIndex];

			/*
			 * If you can finish the player's turn, update the player's
			 * development cards such that the "new" development cards are
			 * appropriately added to the old development card list. Then reset
			 * the new development card list.
			 */
			DevCardList newCards = player.getNewDevCards();
			DevCardList oldCards = player.getOldDevCards();

			oldCards.addDevCards(newCards);
			newCards.resetDevCards();

			/*
			 * Reset the hasPlayedDevelopmentCard boolean.
			 */
			player.setPlayedDevCard(false);

			/*
			 * Change the current turn player index, such that the next player
			 * is able to roll the dice. Set this within the model TurnTracker,
			 * as well set the status to 'Rolling'.
			 */

			int nextPlayer = playerIndex + 1;
			if (nextPlayer > 3) {
				nextPlayer = 0;
			}

			if (model.getTurnTracker().getStatus().toUpperCase()
					.equals("SECONDROUND")) {
				nextPlayer = playerIndex - 1;
			}

			model.getTurnTracker().setCurrentTurn(nextPlayer);
			if (nextPlayer == 0
					&& model.getTurnTracker().getStatus().toUpperCase()
							.equals("FIRSTROUND")) {
				model.getTurnTracker().setStatus("SecondRound");
				nextPlayer = 3;
				model.getTurnTracker().setCurrentTurn(nextPlayer);
			} else if (nextPlayer == -1
					&& model.getTurnTracker().getStatus().toUpperCase()
							.equals("SECONDROUND")) {
				model.getTurnTracker().setStatus("Rolling");
				nextPlayer = 0;
				model.getTurnTracker().setCurrentTurn(nextPlayer);
			} else if (!model.getTurnTracker().getStatus().toUpperCase()
					.equals("FIRSTROUND")
					&& !model.getTurnTracker().getStatus().toUpperCase()
							.equals("SECONDROUND")) {
				model.getTurnTracker().setStatus("Rolling");
			}

			/*
			 * Update game history
			 */
			String name = model.getPlayers()[playerIndex].getName();
			model.getLog().addLine(
					new MessageLine(name + "'s turn ended.", name));

			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException(
					"Unable to finish turn. Invalid input parameters.");
		}
	}
}
