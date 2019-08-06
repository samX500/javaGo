package smallStuff;

public class staticList
{
	public static boolean[] instantiateBooleanList(boolean defaultVal,int size)
	{
		boolean[] list = new boolean[size];
		
		for(int i = 0;i< size;i++)
			list[i] = defaultVal;
		
		return list;
	}
	
}
