package control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jws.Oneway;

import application.Game;
import board.Board;
import boardPiece.BoardPiece;
import boardPiece.BoardPiece.TileStatus;
import fileManagement.ReadFile;
import gui.Gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import memory.Memory;
import smallStuff.*;

public class GoController
{
	public static void buttonInput(EventHandler<ActionEvent> handler, ActionEvent event)
	{
		handler.handle(event);
		Gui.showView();
	}

	public static void placeStone(Game game, Position position)
	{
		boolean suicide = false;

		if (MemShowTerritory.isActive())
			MemShowTerritory.setInactive();
		if (PassCheck.isActive())
			PassCheck.setInactive();

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

		if (!suicide)
		{
			game.getMemory().saveMove(position, game.isBlack() ? Color.black : Color.white);
			game.incrementTurn();
		}
	}

	public static void undo(Game game)
	{
		if (EndOfGame.isGameEnding())
		{
			Gui.removeEndGameControl();
			EndOfGame.removeGameEnding();
			PassCheck.setInactive();
		}
		backATurn(game);
		game.setBoard(game.getMemory().getLastBoard());
	}

	public static void getBoardAt(Game game, int turn)
	{
		// TODO find a good way to deActivate if turn == game.getTurn();
		game.setBoard(game.getMemory().getBoard(turn));
		TurnButton.setActive(turn);
	}

	public static void resetTo(Game game, int turn)
	{
		while (game.getMemory().size() - 1 >= turn)
			backATurn(game);
	}

	public static void backATurn(Game game)
	{
		game.getMemory().removeLastMove();
		game.setTurn(game.getTurn().getTurn() - 1);
	}

	public static void pass(Game game)
	{
		if (PassCheck.isActive())
			EndOfGame.endsGame(game);
		else
		{
			PassCheck.setActive();
			game.getMemory().saveMove(null, null);
			game.incrementTurn();
			Gui.showView();
		}
	}

	public static void showTerritory(Game game, Button button)
	{
		if (MemShowTerritory.isActive())
		{
			button.setText("Show territory");
			Gui.showView();
		} else
		{
			MemShowTerritory.setActive();
			button.setText("Unshow territory");
			Gui.showTerritory();
		}

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
		if (score[0] > score[1])
			Gui.winMessage("Winner", "Black won the game by " + (score[0] - score[1]) + " points!");
		else
			Gui.winMessage("Winner", "White won the game by " + (score[1] - score[0]) + " points!");
	}

	public static void openFile(Game game, Stage stage)
	{
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showSaveDialog(stage);
		Memory moveList = ReadFile.openGame(game, file.toString());

		game = new Game(moveList.getMove(), moveList.getDimension(), moveList.getKomi());
		Gui.setGame(game);
		
		if(EndOfGame.isGameEnding())
			EndOfGame.endsGame(game);
		else
			Gui.showView();
	}

}
