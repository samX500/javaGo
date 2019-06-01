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

	public BoardPiece(Board board, int[] position)
	{
		if (board != null && validatePosition(position, board))
		{
			setBoard(board);
			setPosition(position);
		}
	}

	public BoardPiece(Board board, int[] position, TileStatus status) throws ConstructorException, SuicideException
	{
		if (board != null && validatePosition(position, board))
		{
			setBoard(board);
			setPosition(position);
			setContent(status);
		} else
		{
			 throw new ConstructorException("error creating boardPiece");
		}
		System.out.println(position);
	}

	public Board getBoard()
	{
		return board;
	}

	public void setBoard(Board board)
	{
		this.board = board;
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

	public void setContent(Stone stone)
	{
		content = stone;
	}
	public void setContent(TileStatus status) throws ConstructorException, SuicideException
	{
		switch (status)
		{
		case EMPTY:
			content = null;
			setStatus(TileStatus.EMPTY);
			break;
		case BORDER:
			content = null;
			setStatus(TileStatus.BORDER);
			break;
		case BLACK:
			content = new Stone(board,position,true);
			setStatus(TileStatus.BLACK);
			break;
		case WHITE:
			content = new Stone(board,position,false);
			setStatus(TileStatus.WHITE);
			break;
		}
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

		if (!(position != null && position.length != 0 && position[0] >= 0 && position[1] >= 0
				&& position[0] < board.getLenght() && position[1] < board.getWidth()))
		{
			System.out.println(position[0] + " " + board.getLenght());
			System.out.println(position[1] + " " + board.getWidth());
			System.out.println(position[0] < board.getLenght());
			System.out.println((position[1] < board.getWidth()) + "\n\n");
		}
		return position != null && position.length != 0 && position[0] >= 0 && position[1] >= 0
				&& position[0] < board.getLenght() && position[1] < board.getWidth();
	}
	
	public String toString()
	{
		return position[0]+" ,"+position[1]+"\t:"+content;
	}
}
