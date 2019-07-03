package boardPiece;

import board.Board;
import exception.ConstructorException;

public class Tile extends BoardPiece
{
	public enum TileStatus
	{
		EMPTY, BORDER
	}

	private TileStatus status;

	public Tile(TileStatus status, int x, int y, Board board) throws ConstructorException
	{
		super(x, y, board);
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

	@Override
	public BoardPiece clonePiece(BoardPiece piece)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString()
	{
		String type = getStatus() == TileStatus.EMPTY ? "Empty tile at:" : "Border tile at:";
		return type + getXPosition() + " ," + getYPosition();
	}

	
}
