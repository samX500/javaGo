package boardPiece;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;
import smallStuff.Position;

public abstract class BoardPiece
{
	private Position position;

	BoardPiece(int x, int y)
	{
		position = new Position(x, y);
	}

	public Position getPosition()
	{
		return position;
	}

	public int getXPosition()
	{
		return position.getX();
	}

	public int getYPosition()
	{
		return position.getY();
	}

	public void setXPosition(int x)
	{
		this.position.setX(x);
	}

	public void setYPosition(int y)
	{
		this.position.setY(y);
	}

	public void setPositions(int x, int y)
	{
		this.position.setX(x);
		this.position.setY(y);
	}

	public String toString()
	{
		return position.getX() + " ," + position.getY() + "\t:";
	}
}
