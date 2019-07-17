package application;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;
import memory.Memory;
import smallStuff.Player;
import smallStuff.Turn;

public class Game
{
	private static final Double TIE_BREAKER = 0.5;

	private Board board;
	private Turn turn;
	private Memory memory;
	
	// TODO implement scoring mechanic
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
	public Game(int lenght, int width) throws ConstructorException, SuicideException
	{
		if (Board.validateSize(lenght, width))
		{
			// TODO add memory
			board = new Board(lenght, width);
			turn = new Turn();
			memory = new Memory(lenght,width);
			player1 = new Player();
			player2 = new Player();
			
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
	public Game(int lenght, int width, int blackKomi, int whiteKomi, Board board)
			throws ConstructorException, SuicideException
	{
		// Not sure if it is a good practice to have a constructor where some parameter
		// are useless

		if (board != null)
			this.board = board;
		else if (Board.validateSize(lenght, width))
			board = new Board(lenght, width);
		else
			throw new ConstructorException("Invalid size");

		turn = new Turn();
		player1 = new Player();
		player2 = new Player();
		memory = new Memory(lenght,width);
	}

	public void incrementTurn()
	{
		turn.increase();
	}

	public Turn getTurn()
	{
		return turn;
	}

	public void setTurn(int newturn)
	{
		if (newturn > 0)
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

	public int getScore1()
	{
		return player1.getScore().getScore();
	}
	
	public int getScore2()
	{
		return player2.getScore().getScore();
	}

	public void setScore1(int score)
	{
		player1.getScore().setScore(score);
	}
	
	public void setScore2(int score)
	{
		player2.getScore().setScore(score);
	}

	public void setScores(int score1,int score2)
	{
		player1.getScore().setScore(score1);
		player2.getScore().setScore(score2);
	}

	public Memory getMemory()
	{
		return memory;
	}
}
