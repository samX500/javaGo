package control;

import java.util.HashMap;
import application.Game;
import gui.Gui;
import smallStuff.Color;
import smallStuff.Position;

public class EndOfGame
{
	private static boolean gameEnding = false;

	public static void endsGame(Game game)
	{
		gameEnding = true;
		selectDeadGroup(game);
	}

	public static void selectDeadGroup(Game game)
	{
		HashMap<Position, Color> fadedStone = new HashMap<>();

		Gui.setEndGameControl();
		Gui.showEndGame(fadedStone);
	}

	public static boolean isGameEnding()
	{
		return gameEnding;
	}

	public static void removeGameEnding()
	{
		gameEnding = false;
	}

}
