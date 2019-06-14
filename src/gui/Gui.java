package gui;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.LALOAD;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import application.Game;
import board.Board;
import boardPiece.BoardPiece;
import boardPiece.BoardPiece.*;
import control.GoController;
import boardPiece.Color;
import boardPiece.Stone;
import boardPiece.Tile;
import boardPiece.TileStatus;
import exception.SuicideException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import memory.Memory;

public class Gui extends Application
{
	public static final int BUTTON_SIZE = 30;
	public static final int BLACK_IMAGE = 0;
	public static final int WHITE_IMAGE = 1;
	public static final int EMPTY_IMAGE = 2;
	public static final int BORDER_IMAGE = 3;

	private static Stage mainStage;
	private static List<Background> images;
	private static Game game;
	private static BorderPane display;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		// TODO code app
		game = new Game(19, 19);

		setupGui();

		showView(stage, game.getBoard());

	}

	public void setupGui()
	{
		// TODO maybe add more things
		images = new ArrayList<>();
		loadImages();
	}

	public void showView(Stage stage, Board currentBoard)
	{
		// TODO add more to the gui
		mainStage = stage;
		mainStage.setTitle("Go game");

		GridPane center = makeGrid();
		display = new BorderPane();

		display.setCenter(center);
		showBoard();

		Scene scene = new Scene(display);
		mainStage.setScene(scene);
		mainStage.show();

	}

	private GridPane makeGrid()
	{
		GridPane pane = new GridPane();
		Board currentBoard = game.getBoard();

		for (int i = 0; i < currentBoard.getLenght(); i++)
		{
			for (int j = 0; j < currentBoard.getWidth(); j++)
			{
				Button temp = new Button();
				temp.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
				pane.add(temp, i, j);
			}
		}

		return pane;
	}

	public static void showBoard()
	{
		// TODO try to make this better

		Board currentBoard = game.getBoard();
		GridPane pane = (GridPane) display.getCenter();

		for (int i = 0; i < pane.getChildren().size(); i++)
		{
			Button thisButton = (Button) pane.getChildren().get(i);
			BoardPiece piece = currentBoard.getBoardPiece(i);

			if (piece instanceof Stone)
				thisButton.setBackground(
						((Stone) piece).getColor() == Color.BLACK ? images.get(BLACK_IMAGE) : images.get(WHITE_IMAGE));
			else
				thisButton.setBackground(((Tile) piece).getStatus() == TileStatus.EMPTY ? images.get(EMPTY_IMAGE)
						: images.get(BORDER_IMAGE));

			if (piece instanceof Tile && ((Tile) piece).getStatus() == TileStatus.EMPTY)
			{
				activateButton(thisButton, new int[] { piece.getXPosition(), piece.getYPosition() });
			} else
			{
				disableButton(thisButton);
			}
		}

	}

	private static void disableButton(Button button)
	{
		button.setOnMouseEntered(null);
		button.setOnMouseExited(null);
		button.setOnAction(null);
	}

	private static void activateButton(Button button, int[] position)
	{
		button.setOnMouseEntered(e -> button.setBackground(images.get(game.getPlayer() ? BLACK_IMAGE : WHITE_IMAGE)));
		button.setOnMouseExited(e -> button.setBackground(images.get(EMPTY_IMAGE)));
		button.setOnAction(e -> GoController.placeStone(game, position));
	}

	private void loadImages()
	{
		images.add(loadBackground("goBlack.png"));
		images.add(loadBackground("goWhite.png"));
		images.add(loadBackground("emptyTile.jpg"));
		images.add(loadBackground("borderTile.jpg"));

	}

	private static Background loadBackground(String fileName)
	{
		return new Background(new BackgroundImage(new Image(fileName, BUTTON_SIZE, BUTTON_SIZE, true, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(BUTTON_SIZE, BUTTON_SIZE, true, true, true, false)));
	}
}
