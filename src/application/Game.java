package application;

import java.util.List;

import board.Board;
import boardPiece.Color;
import boardPiece.TileStatus;
import exception.ConstructorException;
import exception.SuicideException;
import gui.Gui;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import memory.Memory;

public class Game
{
	private static final Double TIE_BREAKER = 0.5;

	private Board board;
	private int turn;
	// TODO do things with score
	private Double[] score;
	private Memory memory;

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
			turn = 0;
			score = new Double[] { 0.0, TIE_BREAKER };
			memory = new Memory();
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

		turn = 0;
		score = new Double[] { blackKomi + 0.0, whiteKomi + TIE_BREAKER };
		memory = new Memory();
	}

	public void incrementTurn()
	{
		turn++;
	}

	public int getTurn()
	{
		return turn;
	}

	public void setTurn(int newturn)
	{
		if (newturn > 0)
			turn = newturn;
	}

	public boolean isBlack()
	{
		return turn % 2 == 0;
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

	public Double[] getScore()
	{
		return score;
	}

	public Memory getMemory()
	{
		return memory;
	}

	public void setBlackScore(int score)
	{
		// what is the best way to cast int to Double?
		this.score[0] = score + 0.0;
	}

	public void setWhiteScore(int score)
	{
		// what is the best way to cast int to Double?
		this.score[1] = score + 0.0;
	}

	public void setScores(int[] scores)
	{
		score[0] = (double) scores[0];
		score[1] = scores[1] + TIE_BREAKER;
	}

}
