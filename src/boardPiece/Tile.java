package boardPiece;

public class Tile extends BoardPiece
{
	
	public enum TileStatus
	{
		EMPTY, BORDER
	}
	
	public enum Owner
	{
		NOT_OWNED, WHITE_OWNED, BLACK_OWNED
	}

	private Owner owner;
	private TileStatus status;

	public Tile(TileStatus status, int x, int y) 
	{
		super(x, y);
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
	
	//TODO add some method to deal with owningship

	public String toString()
	{
		String type = getStatus() == TileStatus.EMPTY ? "Empty tile at:" : "Border tile at:";
		return type + getXPosition() + " ," + getYPosition();
	}

}
