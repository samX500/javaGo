package boardPiece;

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

	public BoardPiece(int x, int y, Color color, TileStatus status)
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

	public boolean isStone()
	{
		return status == TileStatus.STONE;
	}

	public String toString()
	{
		return color + ", " + status + ", " + position.getX() + " ," + position.getY();
	}

	public boolean equals(BoardPiece piece)
	{
	
		if (!position.equals(piece.getPosition()))
			return false;
		if (!(getStatus() == piece.getStatus()))
			return false;

		return isStone() ? getColor() == piece.getColor() : true;
	}

}
