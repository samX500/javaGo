package smallStuff;

public class Position 
{
	/**
	 * x coordinate
	 */
	private int x;

	/**
	 * y coordinate
	 */
	private int y;

	/**
	 * Create a position consisting of a x and y coordinate
	 * 
	 * @param xCoordinate x coordinate of the position
	 * @param yCoordinate y coordinate of the position
	 */
	public Position(int x, int y)
	{
		setX(x);
		setY(y);
	}

	public Position(Position position)
	{
		setX(position.getX());
		setY(position.getY());
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Position)
			return this.x == ((Position) other).getX() && this.y == ((Position) other).getY();
		return false;
	}

	public String toString()
	{
		return "X: " + x + " Y: " + y;
	}
}
