package application;

import java.util.Stack;

import board.Board;
import control.GoController;
import exception.ConstructorException;
import memory.Memory;
import memory.Move;
import smallStuff.*;

public class Game
{
	private static final Double TIE_BREAKER = 0.5;

	private Board board;
	private Turn turn;
	private Memory memory;

	private Player player1;
	private Player player2;

	/**
	 * Constructor for normal game
	 * 
	 * @param width  Width of board
	 * @param lenght Lenght of board
	 * 
	 * @throws SuicideException
	 * @throws ConstructorException
	 */
	public Game(int lenght, int width) throws ConstructorException
	{
		if (Board.validateSize(lenght, width))
		{
			board = new Board(lenght, width);
			turn = new Turn();
			player1 = new Player(0.0);
			player2 = new Player(TIE_BREAKER);
			memory = new Memory(lenght, width, new Player[] { player1, player2 });

		} else
			throw new ConstructorException("Invalid board size");
	}

	/**
	 * Constructor for special game (handicap mode)
	 * 
	 * @param width     Width of board
	 * @param lenght    Lenght of board
	 * @param blackKomi Komi for black
	 * @param whiteKomi Komi for white
	 * @param board     Pre-made board
	 * @throws SuicideException
	 * @throws ConstructorException
	 */
	public Game(int lenght, int width, int blackKomi, int whiteKomi, Board board) throws ConstructorException
	{
		if (board != null)
			this.board = board;
		else if (Board.validateSize(lenght, width))
			board = new Board(lenght, width);
		else
			throw new ConstructorException("Invalid size");

		turn = new Turn();
		player1 = new Player(blackKomi+0.0);
		player2 = new Player(whiteKomi+TIE_BREAKER);
		memory = new Memory(lenght, width, new Player[] { player1, player2 });
	}

	/**
	 * Constructor to create a new game from a file
	 * 
	 * @param moveStack List of move made
	 * @param dimension Dimensions of the board
	 * @param komi Komi of white player
	 */
	public Game(Stack<Move> moveStack, Dimension dimension,double komi)
	{
		board = new Board(dimension.getLenght(), dimension.getWidth());
		turn = new Turn();
		player1 = new Player(0.0);
		player2 = new Player(komi + TIE_BREAKER);
		memory = new Memory(dimension, new Player[] { player1, player2 });
		
		System.out.println(moveStack);
		
		while (!moveStack.isEmpty())
		{
			Move move = moveStack.pop();
			if(move.getPosition()==null)
				GoController.pass(this);
			else
				GoController.placeStone(this, move.getPosition());
			
		}
	}
	
	public Double getBlackKomi()
	{
		return player1.getKomi();
	}

	public Double getWhiteKomi()
	{
		return player2.getKomi();
	}

	public void setBlackKomi(int komi)
	{
		player1.setKomi(komi + 0.0);
	}

	public void setWhiteKomi(int komi)

	{
		player2.setKomi(komi + TIE_BREAKER);
	}

	public void incrementTurn()
	{
		turn.increase();
	}

	public Turn getTurn()
	{
		return turn;
	}

	public Player getPlayer1()
	{
		return player1;
	}

	public Player getPlayer2()
	{
		return player2;
	}

	public Player[] getPlayers()
	{
		return new Player[] { player1, player2 };
	}

	public void setTurn(int newturn)
	{
		if (newturn >= 0)
			turn.setTurn(newturn);
	}

	public boolean isBlack()
	{
		return turn.getTurn() % 2 == 0;
	}

	public Board getBoard()
	{
		return board;
	}

	public void setBoard(Board board)
	{
		if (board != null)
			this.board = board;
	}

	public double[] getScore()
	{
		int[] scores = board.countTerritory();

		double[] result = new double[] { scores[0], scores[1] };
		result[0] += player1.getCapture() + player1.getKomi();
		result[1] += player2.getCapture() + player2.getKomi();

		return result;
	}

	public Memory getMemory()
	{
		return memory;
	}

}
