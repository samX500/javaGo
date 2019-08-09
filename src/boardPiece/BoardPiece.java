package boardPiece;

import board.Board;
import boardPiece.BoardPiece.TileStatus;
import exception.ConstructorException;
import exception.SuicideException;
import smallStuff.Color;
import smallStuff.Position;

public class BoardPiece
{
	public enum TileStatus
	{
		EMPTY, BORDER, STONE
	}
	
	private Color color;
	private TileStatus status;
	private Position position;

	public BoardPiece(int x, int y,Color color, TileStatus status)
	{
		position = new Position(x, y);
		this.color = color;
		this.status = status;
	}

	public TileStatus getStatus()
	{
		return status;
	}

	public void setStatus(TileStatus status)
	{
		this.status = status;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
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
	
	public  boolean equals(BoardPiece piece)
	{
		//TODO
		return true;
	}
}
