package server.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import shared.communication.ChangeLogLevelParams;
import shared.utils.ServerResponseException;

/**
 * This command changes the level of logging on the server to one of the
 * following: SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST
 * 
 * @author winstonhurst
 */
public class ChangeLogLevelCommand extends ICommand {
	Level logLevel;
	private static Logger logger;

	static {
		logger = Logger.getLogger("CatanServer");
	}

	/**
	 * 
	 * @param params
	 *            - contains new log setting level (string)
	 */
	public ChangeLogLevelCommand(ChangeLogLevelParams params) {
		/*
		 * If the string stored within ChangeLogLevelParams matches a valid log
		 * level, appropriately set logLevel equal to the matching level. Else,
		 * set logLevel equal to null.
		 */
		if (params.getLogLevel().equals(Level.ALL.toString())) {
			logLevel = Level.ALL;
		} else if (params.getLogLevel().equals(Level.CONFIG.toString())) {
			logLevel = Level.CONFIG;
		} else if (params.getLogLevel().equals(Level.FINE.toString())) {
			logLevel = Level.FINE;
		} else if (params.getLogLevel().equals(Level.FINER.toString())) {
			logLevel = Level.FINER;
		} else if (params.getLogLevel().equals(Level.FINEST.toString())) {
			logLevel = Level.FINEST;
		} else if (params.getLogLevel().equals(Level.INFO.toString())) {
			logLevel = Level.INFO;
		} else if (params.getLogLevel().equals(Level.OFF.toString())) {
			logLevel = Level.OFF;
		} else if (params.getLogLevel().equals(Level.SEVERE.toString())) {
			logLevel = Level.SEVERE;
		} else if (params.getLogLevel().equals(Level.WARNING.toString())) {
			logLevel = Level.WARNING;
		} else {
			logLevel = null;
		}
		this.setType("ChangeLogLevel");

	}

	/**
	 * Changes the state of the server log to the indicated.
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		/*
		 * If logLevel is not null (i.e. a valid log level), set the level of
		 * the logger within the server facade. Else, throw an invalid request
		 * error.
		 */
		if (logLevel != null) {
			logger.setLevel(logLevel);
		} else {
			throw new ServerResponseException("Invalid Request");
		}
	}
}
