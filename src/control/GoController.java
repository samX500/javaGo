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
				if (game.isBlack())
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
			game.memory.saveBoard(currentBoard);
			Gui.addMemory(game.turn);
			game.incrementTurn();
			Gui.showBoard();
			// TODO do something else
		
	}
	
	//TODO I need this method because the of the goBackButton, maybe I can solve this
	public static void undo(Game game)
	{
		
		game.setBoard(game.memory.getBoard(game.memory.getSize()-2));
		Gui.showBoard();
	}
	
	public static void getBoardAt(Game game,int turn)
	{
		game.setBoard(game.memory.getBoard(turn));
		Gui.showBoard();
	}
	
}
