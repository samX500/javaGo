package boardPiece;

import smallStuff.Color;

public class Tile extends BoardPiece
{

	public enum TileStatus
	{
		EMPTY, BORDER
	}

	private Color color;
	private TileStatus status;

	public Tile(TileStatus status, int x, int y)
	{
		super(x, y);
		setStatus(status);
		setColor(Color.colorless);
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

	public String toString()
	{
		String type = getStatus() == TileStatus.EMPTY ? "Empty tile at:" : "Border tile at:";
		return type + getXPosition() + " ," + getYPosition();
	}

	@Override
	public boolean equals(BoardPiece piece)
	{
		return piece instanceof Tile && this.getPosition().equals(piece.getPosition())
				&& this.getStatus() == ((Tile) piece).getStatus();
	}

}
