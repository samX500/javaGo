package control;

import application.Game;
import board.Board;
import boardPiece.BoardPiece.TileStatus;
import gui.Gui;
import smallStuff.*;

public class GoController
{
	public static void placeStone(Game game, Position position)
	{
		boolean suicide = false;

		if (TurnButton.isActive())
		{
			game.setBoard(game.getMemory().getBoard(TurnButton.getTurn()));
			resetTo(game, TurnButton.getTurn());
			TurnButton.setInactive();
		}

		Board currentBoard = game.getBoard();
		Board KOBoard = game.getTurn().getTurn() > 1 ? game.getMemory().getKOBoard(game.getTurn().getTurn() - 1) : null;

		if (game.isBlack())
			currentBoard.setBoardPiece(Color.black, TileStatus.STONE, position.getX(), position.getY());
		else
			currentBoard.setBoardPiece(Color.white, TileStatus.STONE, position.getX(), position.getY());

		suicide = currentBoard.checkDeadPiece(game.getPlayers(), position, KOBoard);

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
		game.setBoard(game.getMemory().getLastBoard());
		Gui.showView();
	}

	public static void getBoardAt(Game game, int turn)
	{
		// TODO find a good way to deActivate if turn == game.getTurn();
		game.setBoard(game.getMemory().getBoard(turn));
		TurnButton.setActive(turn);
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
		game.setTurn(game.getTurn().getTurn() - 1);
	}

	public static void pass(Game game)
	{
		if (PassCheck.isActive() && PassCheck.getTurn() == game.getTurn().getTurn() - 1)
			System.out.println("Game ended");
		// TODO end game
		else
		{
			PassCheck.setActive(game.getTurn().getTurn());
			game.getMemory().saveMove(null, null);
			game.incrementTurn();
			Gui.showView();
		}
	}

	public static void showTerritory(Game game)
	{
		Gui.showTerritory();
	}

}
