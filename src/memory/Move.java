package memory;

import boardPiece.BoardPiece;
import boardPiece.Stone.Color;
import smallStuff.Position;
import smallStuff.Turn;

public class Move
{
	private Position position;
	private Color piece;
	
	public Move(Position position, Color piece)
	{
		setPosition(position);
		setColor(piece);
	}

	public Position getPosition()
	{
		return position;
	}

	public void setPosition(Position position)
	{
		this.position = position;
	}

	public Color getColor()
	{
		return piece;
	}

	public void setColor(Color piece)
	{
		this.piece = piece;
	}
	
	public String toString()
	{
		return piece + " "+position;
	}
}
