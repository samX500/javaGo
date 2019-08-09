package memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import board.Board;
import boardPiece.BoardPiece;
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

	public Memory(int lenght, int width,Player[] players) throws ConstructorException
	{
		this.undoMemory = new Stack<>();
		this.doMemory = new Stack<>();
		this.players = players;
		dimension = new Dimension(lenght, width);
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

		Board board = new Board(stackOpperation.reverseStack(stackOpperation.cloneStack(undoMemory)),dimension,players);

		while (!doMemory.isEmpty())
			undoMemory.push(doMemory.pop());

		return board;
	}
	
	public Board getKOBoard(int turn)
	{
		
		while (undoMemory.size() > turn)
			doMemory.push(undoMemory.pop());

		Board board = new Board(stackOpperation.reverseStack(stackOpperation.cloneStack(undoMemory)),dimension,null);

		while (!doMemory.isEmpty())
			undoMemory.push(doMemory.pop());

		return board;
	}

	public Board getLastBoard()
	{
		return getBoard(this.getSize());
	}

	public void saveMove(Position position, Color piece)
	{
		undoMemory.push(new Move(position, piece));
	}

	public void removeLastMove()
	{
		undoMemory.pop();
	}

	public int getSize()
	{
		return undoMemory.size();
	}

	public String toString()
	{
		return undoMemory + "";
	}

}
