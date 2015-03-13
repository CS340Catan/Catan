package shared.communication;

/**
 * This class contains the information needed by the server to create a new
 * game. It is sent with the '/games/create' request
 * 
 * Domain: randomTiles:True or False randomNumbers:True or False
 * randomPorts:True or False name:String Domain Restraints: name - not null;
 * 
 * @author winstonhurst
 */
public class CreateGameParams {
	/**
	 * Boolean indicating whether or not to create a new game with random tile
	 * placement
	 */
	boolean randomTiles;
	/**
	 * Boolean indicating whether or not to create a new game with random number
	 * assignation
	 */
	boolean randomNumbers;
	/**
	 * Boolean indicating whether or not to create a new game with random port
	 * placement
	 */
	boolean randomPorts;
	String name;

	/**
	 * @pre name cannot be null, randomTiles, randomNumbers and RandomPorts must
	 *      be set to TRUE or FALSE
	 * @post Creates a CreateGameParams object which can then be serialized and
	 *       sent to the server
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @param name
	 *            This constructor will throw an InvlaidInputExceptoin the name
	 *            passed is null or the empty string.
	 * @throws Will
	 *             throw an InvlaidInputExceptoin the name passed is null or the
	 *             empty string.
	 */
	public CreateGameParams(boolean randomTiles, boolean randomNumbers,
			boolean randomPorts, String name) throws InvalidInputException {
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		if (name == null || name.equals("")) {
			throw new InvalidInputException(
					"Game name cannot be null or the empty string");
		}
		this.name = name;
	}

	public boolean isRandomTiles() {
		return randomTiles;
	}

	public void setRandomTiles(boolean randomTiles) {
		this.randomTiles = randomTiles;
	}

	public boolean isRandomNumbers() {
		return randomNumbers;
	}

	public void setRandomNumbers(boolean randomNumbers) {
		this.randomNumbers = randomNumbers;
	}

	public boolean isRandomPorts() {
		return randomPorts;
	}

	public void setRandomPorts(boolean randomPorts) {
		this.randomPorts = randomPorts;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}
}