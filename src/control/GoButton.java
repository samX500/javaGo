package control;

import board.Board;
import boardPiece.BoardPiece;
import boardPiece.TileStatus;
import exception.ConstructorException;
import exception.SuicideException;
import javafx.scene.control.Button;

public class GoButton
{
	public static final int BUTTON_SIZE=50;
	
	private BoardPiece piece;
	private Button button;

	public GoButton(Board board, int[] position, TileStatus status)
			throws ConstructorException, SuicideException
	{
		piece = new BoardPiece(board, position, status);
		button = new Button();
		button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
	}

	public Button getButton()
	{
		return button;
	}

	public BoardPiece getPiece()
	{
		return piece;
	}

	public void setPiece(BoardPiece piece)
	{
		this.piece = piece;
	}

}
