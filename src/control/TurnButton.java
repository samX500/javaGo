package control;

public class TurnButton
{
	/**
	 * Store if a TurnButton has been activated
	 */
	private static boolean isATurnButtonActive = false;
	
	/**
	 * Store what TurnButton was activated
	 */
	private static int turnActivate = 0;
	
	public static boolean isActive()
	{
		return isATurnButtonActive;
	}
	
	public static void setActive(int turn)
	{
		turnActivate = turn;
		isATurnButtonActive = true;
	}
	
	public static void setInactive()
	{
		isATurnButtonActive = false;
	}

	public static int getTurn()
	{
		if(isATurnButtonActive)
			return turnActivate;
		//TODO should this throw exception if it's not active
		//doesn't return if turnButton is deactivated
		return -1;
	}
	
	
}
