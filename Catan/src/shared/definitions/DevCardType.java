package shared.definitions;

public enum DevCardType {

	SOLDIER, YEAR_OF_PLENTY, MONOPOLY, ROAD_BUILD, MONUMENT;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case SOLDIER: return "soldier";
			case YEAR_OF_PLENTY: return "year_of_plenty";
			case MONOPOLY: return "monopoly";
			case ROAD_BUILD: return "road_build";
			case MONUMENT: return "monument";
			default: return "";
		}
	}
}
