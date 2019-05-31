package gui;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.LALOAD;

import application.App;
import board.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Gui
{
	private static Stage mainStage;
	private static List<Background> images;

	public static void main(String[] args)
	{
		// TODO figure out what to do with this main
		App.main(null);
	}

	public static void setupGui()
	{
		// TODO maybe add more things
		images = new ArrayList<>();
		loadImages();
	}

	public static void showView(Stage stage, Board currentBoard)
	{
		// TODO add more to the gui
		mainStage = stage;
		mainStage.setTitle("Test");
		showBoard(currentBoard);
		mainStage.show();
	}

	public static void showBoard(Board currentBoard)
	{
		GridPane board = new GridPane();
		for (int i = 0; i < currentBoard.getLenght(); i++)
		{
			for (int j = 0; j < currentBoard.getWidth(); j++)
			{
				Button temp = new Button();
				temp.setPrefSize(App.BUTTON_SIZE, App.BUTTON_SIZE);
				board.add(temp, i, j);
			}
		}

		Scene scene = new Scene(board);
		for (int i = 0; i < board.getChildren().size(); i++)
		{
			Button thisButton = (Button) board.getChildren().get(i);
			switch (currentBoard.getBoardPiece(i).getStatus())
			{

			case BLACK:
				thisButton.setBackground(images.get(0));
				break;
			case WHITE:
				thisButton.setBackground(images.get(1));
				break;
			case EMPTY:
				thisButton.setBackground(images.get(2));
				break;
			case BORDER:
				thisButton.setBackground(images.get(3));
				break;
			}
		}
		mainStage.setScene(scene);
	}

	private static void loadImages()
	{
		images.add(loadBackground("goBlack.png"));
		images.add(loadBackground("goWhite.png"));
		images.add(loadBackground("emptyTile.jpg"));
		images.add(loadBackground("borderTile.jpg"));

	}

	private static Background loadBackground(String fileName)
	{
		return new Background(new BackgroundImage(new Image(fileName, App.BUTTON_SIZE, App.BUTTON_SIZE, true, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(App.BUTTON_SIZE, App.BUTTON_SIZE, true, true, true, false)));
	}
}
