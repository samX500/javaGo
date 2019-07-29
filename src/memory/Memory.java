package memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import board.Board;
import boardPiece.BoardPiece;
import boardPiece.Stone.Color;
import exception.ConstructorException;
import smallStuff.Dimension;
import smallStuff.Position;
import smallStuff.stackOpperation;

public class Memory
{
	/**
	 * Stack containing the move for each turn
	 */
	private Stack<Move> doMemory;
	private Stack<Move> undoMemory;

	/**
	 * Contains the score for each turn
	 */
	private List<int[]> scoreMemory;

	/**
	 * Dimension of the board this memory operate
	 */
	private Dimension dimension;

	public Memory(int lenght, int width) throws ConstructorException
	{
		this.undoMemory = new Stack<>();
		this.doMemory = new Stack<>();
		this.scoreMemory = new ArrayList<>();
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
		while (undoMemory.size() > turn)
			doMemory.push(undoMemory.pop());

		Board board = new Board(stackOpperation.reverseStack(stackOpperation.cloneStack(undoMemory)),dimension);

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
