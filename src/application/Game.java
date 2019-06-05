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

public class Game
{
	private static final Double TIE_BREAKER = 0.5;

	public Board board;
	public int turn;
	public boolean isFirstPlayer;
	public Double[] score;

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
			//TODO add memory
			board = new Board(lenght, width);
			turn = 0;
			isFirstPlayer = true;
			score = new Double[] { 0.0, TIE_BREAKER };
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
	public Game(int lenght, int width, Double blackKomi, Double whiteKomi, Board board)
			throws ConstructorException, SuicideException
	{
		//Not sure if it is a good practice to have a constructor where some parameter are useless
		
			if (board != null)
				this.board = board;
			else if(Board.validateSize(lenght, width))
				board = new Board(lenght, width);
			else
				throw new ConstructorException("Invalid size");
			
			turn = 0;
			isFirstPlayer = true;
			score = new Double[] { blackKomi, whiteKomi + TIE_BREAKER };
		
	}

	public void incrementTurn()
	{
		turn++;
		if (isFirstPlayer)
			isFirstPlayer = false;
		else
			isFirstPlayer = true;
	}

	public int getTurn()
	{
		return turn;
	}

	public boolean getPlayer()
	{
		return isFirstPlayer;
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	public Double[] getScore()
	{
		return score;
	}
	
	public void setBlackScore(int score)
	{
		//what is the best way to cast int to Double?
		this.score[0]=score+0.0;
	}
	
	public void setWhiteScore(int score)
	{
		//what is the best way to cast int to Double?
		this.score[1]=score+0.0;
	}

}
