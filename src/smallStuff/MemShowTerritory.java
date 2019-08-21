package smallStuff;

public class MemShowTerritory
{
	private static boolean isShowingTerritory = false;
	
	public static void setActive()
	{
		isShowingTerritory = true;
	}
	
	public static void setInactive()
	{
		isShowingTerritory = false;
	}
	
	public static boolean isActive()
	{
		return isShowingTerritory;
	}
	
	
}
