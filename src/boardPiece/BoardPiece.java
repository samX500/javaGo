package boardPiece;

import Board.Board;

public abstract class BoardPiece
{
	private Board board;
	private int[] position;
	private TileStatus status;
	private BoardPiece content;

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

	public BoardPiece getContent()
	{
		return content;
	}

	public void setContent(BoardPiece content)
	{
		this.content = content;
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
}
