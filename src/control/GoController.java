package control;

import application.Game;
import board.Board;
import boardPiece.Color;
import exception.SuicideException;
import gui.Gui;

public class GoController
{
	public static void placeStone(Game game, int[] position)
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
				currentBoard.setStone(Color.BLACK, position[0], position[1]);
			} else
			{
				currentBoard.setStone(Color.WHITE, position[0], position[1]);
			}

		} catch (SuicideException error)
		{
			// TODO implement suicide mechanic
		}
		game.getMemory().saveBoard(currentBoard);
		Gui.addMemory(game.getTurn());
		game.incrementTurn();
		Gui.showBoard();
		// TODO do something else

	}

	// TODO I need this method because the of the goBackButton, maybe I can solve
	// this
	public static void undo(Game game)
	{
		game.setBoard(game.getMemory().getBoard(game.getMemory().getSize() - 2));
		resetTo(game, game.getTurn());
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
		while (game.getMemory().getSize() > turn+1)
		{
			System.out.println(game.getMemory().getSize()+"   "+turn);
			game.setTurn(game.getTurn()-1);
			game.getMemory().removeLastBoard();
			Gui.removeMemory(game.getTurn());
		}
	}

}
