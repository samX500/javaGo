package gui;

import application.Game;
import exception.ConstructorException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Menu
{
	// TODO Find a better way to get that
	static Game game;

	/**
	 * Create the board on the demand of the users
	 * 
	 * @throws SuicideException
	 * @throws ConstructorException
	 */
	public static Game createGame() throws ConstructorException
	{
		// TODO maybe this could go in another class

		Stage stage = new Stage();
		Label question = new Label("Do you want to play a normal game or special game?");
		Button normalGame = new Button("Normal game");
		Button specialGame = new Button("Special game");

		BorderPane pane = new BorderPane();
		pane.setTop(question);
		pane.setLeft(normalGame);
		pane.setRight(specialGame);

		normalGame.setOnAction(e -> {

			try
			{
				game = new Game(9, 9);
			} catch (ConstructorException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			stage.hide();
		});

		specialGame.setOnAction(e -> {
			try
			{
				game = new Game(19, 19);
			} catch (ConstructorException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			stage.hide();
		});

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();

		// TODO remove this

		return game;
	}
}
