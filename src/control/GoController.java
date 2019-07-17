package control;

import application.Game;
import board.Board;
import boardPiece.Stone;
import boardPiece.Stone.Color;
import exception.SuicideException;
import gui.Gui;
import smallStuff.Position;

public class GoController
{
	public static void placeStone(Game game, Position position)
	{
		System.out.println(game.getTurn());
		if (TurnButton.isActive())
		{
			resetTo(game, TurnButton.getTurn());
			TurnButton.setInactive();
		}

		Board currentBoard = game.getBoard();

		if (game.isBlack())
			currentBoard.setStone(Color.BLACK, position.getX(), position.getY());
		else
			currentBoard.setStone(Color.WHITE, position.getX(), position.getY());

		currentBoard.removeDeadPieces();

		game.getMemory().saveMove(position, game.isBlack() ? Color.BLACK : Color.WHITE);
		//Gui.addMemory(game.getTurn());
		game.incrementTurn();
		Gui.showView();
		
		System.out.println(game.getMemory());
	}

	public static void undo(Game game)
	{
		backATurn(game);
		Gui.showView();
	}

	public static void getBoardAt(Game game, int turn)
	{
		game.setBoard(game.getMemory().getBoard(turn));
		TurnButton.setActive(turn);
		Gui.showView();
	}

	public static void resetTo(Game game, int turn)
	{
		//TODO check out the >
		while (game.getMemory().getSize() - 1 >= turn)
			backATurn(game);
		
		Gui.showView();
	}

	public static void backATurn(Game game)
	{
		game.getMemory().removeLastMove();
		game.setBoard(game.getMemory().getLastBoard());
		game.setTurn(game.getTurn().getTurn() - 1);
		Gui.removeMemory(game.getTurn().getTurn());
	
	}

}
