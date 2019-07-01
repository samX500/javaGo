package smallStuff;

public class Turn
{
	/**
	 * Integer that represent a turn
	 */
	private int turn;

	/**
	 * Create a new turn
	 */
	public Turn()
	{
		setTurn(0);
	}

	public int getTurn()
	{
		return turn;
	}

	public void setTurn(int turn)
	{
		this.turn = turn;
	}

	public void increase()
	{
		turn++;
	}

	public String toString()
	{
		return "Turn " + (turn + 1);
	}
}
