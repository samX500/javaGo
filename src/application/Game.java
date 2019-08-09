package application;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;
import memory.Memory;
import smallStuff.*;

public class Game
{
	private static final Double TIE_BREAKER = 0.5;

	private Board board;
	private Turn turn;
	private Memory memory;

	private Player player1;
	private Player player2;
	private Komi blackKomi;
	private Komi whiteKomi;

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
			// TODO add memory
			board = new Board(lenght, width);
			turn = new Turn();
			player1 = new Player();
			player2 = new Player();
			blackKomi = new Komi(0.0);
			whiteKomi = new Komi(TIE_BREAKER);
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
	public Game(int lenght, int width, int blackKomi, int whiteKomi, Board board)
			throws ConstructorException, SuicideException
	{
		if (board != null)
			this.board = board;
		else if (Board.validateSize(lenght, width))
			board = new Board(lenght, width);
		else
			throw new ConstructorException("Invalid size");

		turn = new Turn();
		player1 = new Player();
		player2 = new Player();
		this.blackKomi = new Komi(blackKomi + 0.0);
		this.whiteKomi = new Komi(whiteKomi + TIE_BREAKER);
		memory = new Memory(lenght, width, new Player[] { player1, player2 });
	}

	public Double getBlackKomi()
	{
		return blackKomi.getKomi();
	}

	public Double getWhiteKomi()
	{
		return whiteKomi.getKomi();
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

	public int[] getScore()
	{
		int[] scores = board.countTerritory();

		scores[0] += player1.getCapture();
		scores[1] += player2.getCapture();

		return scores;
	}

	public Memory getMemory()
	{
		return memory;
	}
}
