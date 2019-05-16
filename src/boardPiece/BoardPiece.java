package boardPiece;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;

public class BoardPiece
{
	private Board board;
	private int[] position;
	private TileStatus status;
	private Stone content;

	public BoardPiece()
	{
		// TODO empty constructor, check for better coding of super call in
		// children class
	}

	public BoardPiece(Board board, int[] position, TileStatus status)
			throws ConstructorException, SuicideException
	{
		if (board != null && validatePosition(position))
		{
			setBoard(board);
			setPosition(position);
			setContent(status);
		}
	}

	public Board getBoard()
	{
		return board;
	}

	public TileStatus getStatus()
	{
		return status;
	}

	public void setStatus(TileStatus status)
	{
		this.status = status;
	}

	public Stone getContent()
	{
		return content;
	}

	public void setContent(TileStatus status) throws ConstructorException, SuicideException
	{
		switch (status)
		{
			case EMPTY:
				content = null;
				break;
			case BORDER:
				content = null;
				break;
			case BLACK:
				content = new Stone(true);
				break;
			case WHITE:
				content = new Stone(false);
				break;
		}
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}

	public int[] getPosition()
	{
		return position;
	}

	public int getXPosition()
	{
		return position[0];
	}

	public int getYPosition()
	{
		return position[1];
	}

	public void setPosition(int[] position)
	{
		this.position = position;
	}

	public boolean validatePosition(int[] position)
	{
		return position != null && position.length != 0 && position[0] > 0
				&& position[1] > 0 && position[0] < getBoard().getLenght()
				&& position[1] < getBoard().getWidth();
	}
}
