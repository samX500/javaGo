package memory;

import java.util.Stack;
import board.Board;
import exception.ConstructorException;
import smallStuff.*;

public class Memory
{
	/**
	 * Stack containing the move for each turn
	 */
	private Stack<Move> doMemory;
	private Stack<Move> undoMemory;

	/**
	 * List containing the players. To calculate points
	 */
	private Player[] players;

	/**
	 * Dimension of the board this memory operate
	 */
	private Dimension dimension;

	public Memory(int lenght, int width,Player[] players)
	{
		this.undoMemory = new Stack<>();
		this.doMemory = new Stack<>();
		this.players = players;
		dimension = new Dimension(lenght, width);
	}
	
	public Memory(Dimension dimension,Player[] players)
	{
		this.undoMemory = new Stack<>();
		this.doMemory = new Stack<>();
		this.players = players;
		this.dimension = new Dimension(dimension);
	}

	/**
	 * return a copy of the board at the turn passed as a parameter
	 * 
	 * @param turn turn of the board wanted
	 * @return copy of the board at the turn specified
	 */
	public Board getBoard(int turn)
	{
		players[0].setCapture(0);
		players[1].setCapture(0);
		
		while (undoMemory.size() > turn)
			doMemory.push(undoMemory.pop());

		Board board = new Board(StackOpperation.reverseStack(StackOpperation.cloneStack(undoMemory)),dimension,players);

		while (!doMemory.isEmpty())
			undoMemory.push(doMemory.pop());

		return board;
	}
	
	public Board getKOBoard(int turn)
	{
		
		while (undoMemory.size() > turn)
			doMemory.push(undoMemory.pop());

		Board board = new Board(StackOpperation.reverseStack(StackOpperation.cloneStack(undoMemory)),dimension,null);

		while (!doMemory.isEmpty())
			undoMemory.push(doMemory.pop());

		return board;
	}

	public Board getLastBoard()
	{
		return getBoard(this.size());
	}

	public void saveMove(Position position, Color piece)
	{
		undoMemory.push(new Move(position, piece));
	}

	public void removeLastMove()
	{
		undoMemory.pop();
	}

	public int size()
	{
		return undoMemory.size();
	}

	public Stack<Move> getMove()
	{
		return StackOpperation.reverseStack(StackOpperation.cloneStack(undoMemory));
	}
	
	public double getKomi()
	{
		return players[1].getKomi();
	}
	
	public void setKomi(int komi)
	{
		players[1].setKomi(komi+0.0);
	}
	
	public Dimension getDimension()
	{
		return dimension;
	}
	
	public void setDimension(int lenght, int width)
	{
		dimension = new Dimension(lenght, width);
	}
	
	public String toString()
	{
		return undoMemory + "";
	}

}
