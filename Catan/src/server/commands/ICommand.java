package server.commands;

import shared.utils.Serializer;
import shared.utils.ServerResponseException;

public abstract class ICommand {

	private String type = "";

	public abstract void execute() throws ServerResponseException;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toJSONString() {
		return Serializer.serialize(this);
	}

	/**
	 * This function will convert any JSON string into a ICommand, given that
	 * the inputed string is a valid command that can be executed on the server.
	 * 
	 * @param commandJSONString
	 *            JSON string that is to be converted into a command.
	 * @return Valid Command or null if invalid string.
	 */
	public static ICommand fromJSONString(String commandJSONString) {
		if (commandJSONString.contains("AcceptTrade")) {
			return (AcceptTradeCommand) Serializer.deserialize(
					commandJSONString, AcceptTradeCommand.class);
		} else if (commandJSONString.contains("BuildCity")) {
			return (BuildCityCommand) Serializer.deserialize(commandJSONString,
					BuildCityCommand.class);
		} else if (commandJSONString.contains("BuildRoad")) {
			return (BuildRoadCommand) Serializer.deserialize(commandJSONString,
					BuildRoadCommand.class);
		} else if (commandJSONString.contains("BuildSettlement")) {
			return (BuildSettlementCommand) Serializer.deserialize(
					commandJSONString, BuildSettlementCommand.class);
		} else if (commandJSONString.contains("BuyDevCard")) {
			return (BuyDevCardCommand) Serializer.deserialize(
					commandJSONString, BuyDevCardCommand.class);
		} else if (commandJSONString.contains("ChangeLogLevel")) {
			return (ChangeLogLevelCommand) Serializer.deserialize(
					commandJSONString, ChangeLogLevelCommand.class);
		} else if (commandJSONString.contains("DiscardCards")) {
			return (DiscardCardsCommand) Serializer.deserialize(
					commandJSONString, DiscardCardsCommand.class);
		} else if (commandJSONString.contains("FinishTurn")) {
			return (FinishTurnCommand) Serializer.deserialize(
					commandJSONString, FinishTurnCommand.class);
		} else if (commandJSONString.contains("GetCommands")) {
			return (GetCommandsCommand) Serializer.deserialize(
					commandJSONString, GetCommandsCommand.class);
		} else if (commandJSONString.contains("JoinGame")) {
			return (JoinGameCommand) Serializer.deserialize(commandJSONString,
					JoinGameCommand.class);
		} else if (commandJSONString.contains("LoadGame")) {
			return (LoadGameCommand) Serializer.deserialize(commandJSONString,
					LoadGameCommand.class);
		} else if (commandJSONString.contains("Login")) {
			return (LoginCommand) Serializer.deserialize(commandJSONString,
					LoginCommand.class);
		} else if (commandJSONString.contains("MaritimeTrade")) {
			return (MaritimeTradeCommand) Serializer.deserialize(
					commandJSONString, MaritimeTradeCommand.class);
		} else if (commandJSONString.contains("OfferTrade")) {
			return (OfferTradeCommand) Serializer.deserialize(
					commandJSONString, OfferTradeCommand.class);
		} else if (commandJSONString.contains("PlayMonopoly")) {
			return (PlayMonopolyCommand) Serializer.deserialize(
					commandJSONString, PlayMonopolyCommand.class);
		} else if (commandJSONString.contains("PlayMonument")) {
			return (PlayMonumentCommand) Serializer.deserialize(
					commandJSONString, PlayMonumentCommand.class);
		} else if (commandJSONString.contains("PlayRoadBuilding")) {
			return (PlayRoadBuildingCommand) Serializer.deserialize(
					commandJSONString, PlayRoadBuildingCommand.class);
		} else if (commandJSONString.contains("PlaySoldier")) {
			return (PlaySoldierCommand) Serializer.deserialize(
					commandJSONString, PlaySoldierCommand.class);
		} else if (commandJSONString.contains("PlayYearOfPlenty")) {
			return (PlayYearOfPlentyCommand) Serializer.deserialize(
					commandJSONString, PlayYearOfPlentyCommand.class);
		} else if (commandJSONString.contains("PostCommands")) {
			return (PostCommandsCommand) Serializer.deserialize(
					commandJSONString, PostCommandsCommand.class);
		} else if (commandJSONString.contains("Register")) {
			return (RegisterCommand) Serializer.deserialize(commandJSONString,
					RegisterCommand.class);
		} else if (commandJSONString.contains("ResetGame")) {
			return (ResetGameCommand) Serializer.deserialize(commandJSONString,
					ResetGameCommand.class);
		} else if (commandJSONString.contains("RobPlayer")) {
			return (RobPlayerCommand) Serializer.deserialize(commandJSONString,
					RobPlayerCommand.class);
		} else if (commandJSONString.contains("RollNumber")) {
			return (RollNumberCommand) Serializer.deserialize(
					commandJSONString, RollNumberCommand.class);
		} else if (commandJSONString.contains("SaveGame")) {
			return (SaveGameCommand) Serializer.deserialize(commandJSONString,
					SaveGameCommand.class);
		} else if (commandJSONString.contains("SendChat")) {
			return (SendChatCommand) Serializer.deserialize(commandJSONString,
					SendChatCommand.class);
		} else {
			return null;
		}
	}
}
