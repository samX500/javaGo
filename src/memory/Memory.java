package memory;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import exception.ConstructorException;

public class Memory
{
	/**
	 * Contains the board for each turn in this path
	 */
	private List<Board> memory;
	
	/**
	 * Contains the score for each turn
	 */
	private List<Double[]> scoreMemory;

	public Memory() throws ConstructorException
	{
		this.memory = new ArrayList<>();
		this.scoreMemory = new ArrayList<>();
	}

	public Board getBoard(int boardAtTurn)
	{
		return memory.get(boardAtTurn);
	}

	public void saveBoard(Board board) 
	{
		if (board != null)
			try
			{
				memory.add(new Board(board));
			} catch (ConstructorException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void removeLastBoard()
	{
		memory.remove(memory.size()-1);
	}
	
	public int getSize()
	{
		return memory.size();
	}
	
	public String toString()
	{
		return memory+"";
	}
	
}
