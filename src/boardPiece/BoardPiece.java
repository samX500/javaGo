package boardPiece;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;

public abstract class BoardPiece
{
	private Board board;
	private int[] position;

	BoardPiece(int[] position, Board board) throws ConstructorException
	{
		if (board != null && validatePosition(position, board))
		{
			setPosition(position);
			setBoard(board);
		} else
			throw new ConstructorException("Position invalid");
	}

	public Board getBoard()
	{
		return board;
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}

	public void setContent()
	{
		// TODO
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

	public boolean validatePosition(int[] position, Board board)
	{
		return position != null && position.length != 0 && position[0] >= 0 && position[1] >= 0
				&& position[0] < board.getLenght() && position[1] < board.getWidth();
	}

	public String toString()
	{
		return position[0] + " ," + position[1] + "\t:";
	}
}
