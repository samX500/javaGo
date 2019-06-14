package boardPiece;

import board.Board;
import exception.ConstructorException;

public class Tile extends BoardPiece
{
	private TileStatus status;

	public Tile(TileStatus status, int[] position, Board board) throws ConstructorException
	{
		super(position, board);
		setStatus(status);
	}
	
	public TileStatus getStatus()
	{
		return status;
	}

	public void setStatus(TileStatus status)
	{
		this.status = status;
	}

	public String toString()
	{
		String type = getStatus()==TileStatus.EMPTY?"Empty tile at:":"Border tile at:";
		return type + getXPosition() + " ," + getYPosition();
	}
}