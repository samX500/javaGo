package smallStuff;

public enum Color
{
	black(0,1),white(1,0),colorless(2,2);

	private int value;
	private int opposite;
	
	private Color(int value,int opposite)
	{
		this.value = value;
		this.opposite = opposite;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getOpposite()
	{
		return opposite;
	}
}
