package control;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

import application.Game;
import board.Board;
import boardPiece.BoardPiece;
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
		if (EndOfGame.isGameEnding())
		{
			Gui.removeEndGameControl();
			EndOfGame.removeGameEnding();
		}
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
			EndOfGame.endsGame(game);
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

	public static void fadeStone(Game game, Position position, HashMap<Position, Color> fadedStone)
	{
		Board board = game.getBoard();
		BoardPiece piece = board.getBoardPiece(position);

		if (piece.getStatus() == TileStatus.STONE)
			board.floodFade(piece.getColor(), position, fadedStone, new ArrayList<Position>(), game.getPlayers());
		else if (fadedStone.containsKey(position))
			board.floodUnfade(fadedStone.get(position), position, fadedStone, new ArrayList<Position>(),
					game.getPlayers());
		board.countTerritory();
		Gui.showPoints();
		Gui.showEndGame(fadedStone);
	}

	public static void confirmEnd(Game game)
	{
		double[] score = game.getScore();
		if(score[0]>score[1])
			Gui.winMessage("Winner", "Black won the game by "+(score[0]-score[1])+" points!");
		else
			Gui.winMessage("Winner", "White won the game by "+(score[1]-score[0])+" points!");
	}

}
