package control;

import application.Game;
import board.Board;
import boardPiece.Stone.Color;
import exception.SuicideException;
import gui.Gui;
import smallStuff.Position;

public class GoController
{
	public static void placeStone(Game game, Position position)
	{
		Board currentBoard = game.getBoard();
		if (TurnButton.isActive())
		{
			resetTo(game, TurnButton.getTurn());
			TurnButton.setInactive();
		}

		try
		{
			if (game.isBlack())
			{
				currentBoard.setStone(Color.BLACK, position.getX(), position.getY());
			} else
			{
				currentBoard.setStone(Color.WHITE, position.getX(), position.getY());
			}

		} catch (SuicideException error)
		{
			// TODO implement suicide mechanic
		}
		game.getMemory().saveBoard(currentBoard);
		Gui.addMemory(game.getTurn());
		game.incrementTurn();
		Gui.showBoard();

	}

	// TODO I need this method because the of the goBackButton, maybe I can solve
	// this
	public static void undo(Game game)
	{
		game.setBoard(game.getMemory().getBoard(game.getMemory().getSize() - 1));
		resetTo(game, game.getTurn().getTurn());
		Gui.showBoard();
	}

	public static void getBoardAt(Game game, int turn)
	{
		game.setBoard(game.getMemory().getBoard(turn));
		TurnButton.setActive(turn);
		Gui.showBoard();
	}

	public static void resetTo(Game game, int turn)
	{
		while (game.getMemory().getSize()-1 > turn)
		{
			game.setTurn(game.getTurn().getTurn()-1);
			game.getMemory().removeLastBoard();
			Gui.removeMemory(game.getTurn().getTurn());
		}
	}

}
