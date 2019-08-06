package control;

import application.Game;
import board.Board;
import boardPiece.Stone;
import exception.SuicideException;
import gui.Gui;
import smallStuff.*;

public class GoController
{
	public static void placeStone(Game game, Position position)
	{
		boolean suicide = false;

		if (TurnButton.isActive())
		{
			resetTo(game, TurnButton.getTurn());
			TurnButton.setInactive();
		}

		Board currentBoard = game.getBoard();
		Board KOBoard = game.getTurn().getTurn() > 1 ? game.getMemory().getBoard(game.getTurn().getTurn() - 2) : null;

		if (game.isBlack())
			currentBoard.setStone(Color.black, position.getX(), position.getY());
		else
			currentBoard.setStone(Color.white, position.getX(), position.getY());

		suicide = currentBoard.removeDeadPieces(game.getPlayers(), position, KOBoard);

		if (suicide)
			Gui.showView();
		else
		{
			game.getMemory().saveMove(position, game.isBlack() ? Color.black : Color.white);
			game.incrementTurn();
			Gui.showView();
		}
	}

	public static void undo(Game game)
	{
		backATurn(game);
		Gui.showView();
	}

	public static void getBoardAt(Game game, int turn)
	{
		// TODO find a good way to deActivate if turn == game.getTurn();
		game.setBoard(game.getMemory().getBoard(turn));
		TurnButton.setActive(turn);
		game.getBoard().removeDeadPieces(game.getPlayers(), null, null);
		Gui.showView();
	}

	public static void resetTo(Game game, int turn)
	{
		while (game.getMemory().getSize() - 1 >= turn)
			backATurn(game);
	}

	public static void backATurn(Game game)
	{
		game.getMemory().removeLastMove();
		game.setBoard(game.getMemory().getLastBoard());
		game.setTurn(game.getTurn().getTurn() - 1);
	}

	public static void countPoint(Game game)
	{
		int[] score = game.getScore();
		System.out.println(score[0] + " " + score[1]);
	}

}
