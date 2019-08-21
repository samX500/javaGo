package smallStuff;

public class PassCheck
{
	private static boolean isActive = false;

	public static boolean isActive()
	{
		return isActive;
	}

	public static void setActive()
	{
		isActive = true;
	}

	public static void setInactive()
	{
		isActive = false;
	}

}
