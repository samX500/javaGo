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
	
	public Dimension(Dimension dimension)
	{
		setLenght(dimension.getLenght());
		setWidth(dimension.getWidth());
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

	public void addDim(int toAdd)
	{
		lenght += toAdd;
		width += toAdd;
	}
	
	public void removeDim(int toRemove)
	{
		addDim(-1*toRemove);
	}
	
	public int getArea()
	{
		return width*lenght;
	}
	
	public boolean equal(Dimension dimension)
	{
		return dimension != null && lenght == dimension.getLenght() && width == dimension.getWidth();
	}

	/**
	 * Return the size of the dimension if width == lenght or empty if width != lenght
	 * @return size of the dimension if width == lenght or empty if width != lenght
	 */
	public String squareDim()
	{
		if(width==lenght)
			return ""+width;
		return "";
	}
	
	public Dimension clone()
	{
		return new Dimension(this);
	}
	
	public String toString()
	{
		return "lenght: " + lenght + ", width: " + width;
	}

	
}
