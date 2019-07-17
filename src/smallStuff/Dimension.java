package smallStuff;

public class Dimension
{
	private int lenght;
	private int width;
	
	public Dimension(int lenght, int width)
	{
		setLenght(lenght);
		setWidth(width);
	}

	public int getLenght()
	{
		return lenght;
	}

	public void setLenght(int lenght)
	{
		this.lenght = lenght;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}
}
