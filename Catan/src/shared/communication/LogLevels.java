package shared.communication;
/**
 * 
 * @author winstonhurst
 * This enum defines the possible settings for logging on the server
 */
public enum LogLevels {
	SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST;
	
	/**
	 * The integer value associated with each enum value;
	 */
	int intFlag;
	
	static{
		SEVERE.intFlag = 1;
		WARNING.intFlag = 2;
		INFO.intFlag = 3;
		CONFIG.intFlag = 4;
		FINE.intFlag = 5;
		FINER.intFlag = 6;
		FINEST.intFlag = 7;
	}
	
	public int getColor()
	{
		return intFlag;
	}
}
