package gui;

import javafx.scene.control.TextField;

import java.util.Optional;

import application.Game;
import board.Board;
import exception.ConstructorException;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Menu
{

	public static final int MIN_BOARD_SIZE = 2;
	public static final int MAX_BOARD_SIZE = 26;

	/**
	 * Create the board on the demand of the users
	 * 
	 * @throws SuicideException
	 * @throws ConstructorException
	 */
	public static Game createGame() throws ConstructorException
	{
		Stage stage = new Stage();
		Label question = new Label("Choose the dimension of the board");

		VBox hBox = new VBox();
		VBox vBox = new VBox();
		HBox button = new HBox();

		Label hLabel = new Label("Horizontal dimension");
		Label vLabel = new Label("Vertical dimension");

		TextField hText = new TextField("9");
		TextField vText = new TextField("9");

		Button confirmDimension = new Button("Confirm dimensions");

		hBox.getChildren().addAll(hLabel, hText);
		vBox.getChildren().addAll(vLabel, vText);
		button.getChildren().addAll(confirmDimension);

		BorderPane pane = new BorderPane();
		pane.setTop(question);
		pane.setLeft(hBox);
		pane.setRight(vBox);
		pane.setBottom(button);

		confirmDimension.setOnAction(e -> confirmDimension(hText, vText, stage));

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setOnCloseRequest(e -> closeProgram(e));
		stage.showAndWait();

		// TODO make a custom closing event for the "X"

		int hDimension = Integer.parseInt(hText.getText())+Board.BORDER;
		int vDimension = Integer.parseInt(vText.getText())+Board.BORDER;

		return new Game(hDimension, vDimension);
	}

	public static void confirmDimension(TextField hText, TextField vText, Stage stage)
	{
		int hDim = 0;
		int vDim = 0;
		boolean dimensionIsCorrect = true;

		String warningTitle = "Error when creating the board";
		String notAnInt = "You need to input integer for the dimensions of the board";
		String sizeTooSmall = "You need to input an integer greater than 2";
		String sizeTooBig = "You need to input an integer smaller than 26";

		try
		{
			hDim = Integer.parseInt(hText.getText());
			vDim = Integer.parseInt(vText.getText());

			if (hDim <= MIN_BOARD_SIZE || vDim <= MIN_BOARD_SIZE)
			{
				dimensionIsCorrect = false;
				warningWindow(warningTitle, sizeTooSmall);
			} else if (hDim > MAX_BOARD_SIZE || vDim > MAX_BOARD_SIZE)
			{
				dimensionIsCorrect = false;
				warningWindow(warningTitle, sizeTooBig);
			}

		} catch (NumberFormatException e)
		{
			dimensionIsCorrect = false;
			warningWindow(warningTitle, notAnInt);
		}

		if (dimensionIsCorrect)
			stage.hide();

	}

	public static void warningWindow(String title, String warning)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(warning);

		alert.showAndWait();
	}

	/**
	 * Ask a string to the user and returns it
	 * @return string inputed by the user
	 */
	public static String askString(String message)
	{
		Stage window = new Stage();
		BorderPane pane = new BorderPane();
		
		Label label = new Label(message);
		TextField textField = new TextField();
		Button confirmButton = new Button("Confirm");
		
		confirmButton.setOnAction(e->window.hide());
		pane.setTop(label);
		pane.setCenter(textField);
		pane.setBottom(confirmButton);
		
		Scene scene = new Scene(pane);
		window.setScene(scene);
		//prevent the user from closing the window
		window.setOnCloseRequest(e -> e.consume());
		window.showAndWait();
		
		return textField.getText();
	}
	
	public static void closeProgram(WindowEvent closingEvent)
	{
		// Stops the normal closing event.
		closingEvent.consume();

		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("Confirm closing request");
		confirmation.setContentText("Are you sure you want to close the program?");

		if (confirmation.showAndWait().get() == ButtonType.OK)
			System.exit(0);
	}

}
