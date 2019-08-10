package smallStuff;

public class PassCheck
{
	private static int turn = 0;
	private static boolean isActive = false;

	public static boolean isActive()
	{
		return isActive;
	}

	public static void setActive(int passTurn)
	{
		isActive = true;
		turn = passTurn;
	}

	public static void setInactive()
	{
		isActive = false;
	}

	public static int getTurn()
	{
		if (isActive)
			return turn;
		return -1;
	}
}
