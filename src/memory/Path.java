package memory;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import exception.ConstructorException;

public class Path
{
	/**
	 * Contains the board for each turn in this path
	 */
	private List<Board> memory;

	/**
	 * Turn on which this path was created
	 */
	private int turn;

	/**
	 * Pointer to the previous path
	 */
	private Path previousPath;

	/**
	 * List containing future path created using this path as the source
	 */
	private List<Path> node;

	public Path(int turn, Path previousPath) throws ConstructorException
	{
		//TODO maybe check if the turn make sense
		if (turn >= 0)
		{
			setTurn(turn);
			setPreviousPath(previousPath);
			this.node = new ArrayList<>();
			this.memory = new ArrayList<>();
		} else
			throw new ConstructorException();
	}

	public Board getBoard(int boardAtTurn)
	{
		return memory.get(boardAtTurn);
	}
	
	public int getTurn()
	{
		return turn;
	}

	public void setTurn(int turn)
	{
		if (turn >= 0)
			this.turn = turn;
	}

	public Path getPreviousPath()
	{
		return previousPath;
	}

	public void setPreviousPath(Path previousPath)
	{
		if (previousPath != null)
			this.previousPath = previousPath;
	}

	public List<Path> getNode()
	{
		return node;
	}
	
	public void saveBoard(Board board)
	{
		if (board != null)
			memory.add(board);
	}

	public void newPath(int creationTurn) throws ConstructorException
	{
		//TODO maybe look at what to put for turn
		node.add(new Path(creationTurn, this));
	}
}
