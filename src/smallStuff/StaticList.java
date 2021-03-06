package smallStuff;

public class StaticList
{
	public static boolean[] instantiateBooleanList(boolean defaultVal, int size)
	{
		boolean[] list = new boolean[size];

		for (int i = 0; i < size; i++)
			list[i] = defaultVal;

		return list;
	}

	public static void showBooleanList(boolean[] booleanList)
	{
		String list = "[";
		for (int i = 0; i < booleanList.length; i++)
		{
			list += booleanList[i] + ", ";
		}
		System.out.println(list+"]");
	}
}
