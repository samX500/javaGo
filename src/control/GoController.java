package control;

import application.Game;
import board.Board;
import boardPiece.Color;
import exception.SuicideException;
import gui.Gui;

public class GoController
{
	public static void placeStone(Game game,int[] position)
	{
			Board currentBoard = game.getBoard();
			try
			{
				if (game.getPlayer())
				{
					currentBoard.setStone(Color.BLACK, position[0],position[1]);
				} else
				{
					currentBoard.setStone(Color.WHITE, position[0], position[1]);
				}

			} catch (SuicideException error)
			{
				// TODO implement suicide mechanic
			}
			game.incrementTurn();
			Gui.showBoard();
			// showBoard(pane);
			// TODO do something else
		
	}
	
}
