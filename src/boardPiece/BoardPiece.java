package boardPiece;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;
import smallStuff.Position;

public abstract class BoardPiece
{
	private Board board;
	private Position position;

	BoardPiece(int x, int y, Board board) throws ConstructorException
	{
		if (board != null && validatePosition(x, y, board))
		{
			position = new Position(x, y);
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

	public boolean validatePosition(int x, int y, Board board)
	{
		return x >= 0 && y >= 0 && x < board.getLenght() && y < board.getWidth();
	}

	public String toString()
	{
		return position.getX() + " ," + position.getY() + "\t:";
	}
}
