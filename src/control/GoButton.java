package control;

import boardPiece.BoardPiece;
import boardPiece.Tile;
import javafx.scene.control.Button;

public class GoButton
{
	private BoardPiece piece;
	private Button button;
	
	public BoardPiece getPiece()
	{
		return piece;
	}
	public void setPiece(BoardPiece piece)
	{
		this.piece = piece;
	}
	
}
