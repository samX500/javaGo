package gui;

import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.List;

import application.Game;
import board.Board;
import boardPiece.BoardPiece;
import control.GoController;
import boardPiece.Stone.Color;
import boardPiece.Stone;
import boardPiece.Tile;
import boardPiece.Tile.TileStatus;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import smallStuff.Position;
import smallStuff.Turn;

public class Gui extends Application
{
	public static final int BUTTON_SIZE = 35;
			
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
		loadAssets(stage);
		game = Menu.createGame();
		setupGui(stage, game.getBoard());
	}

	/**
	 * Loads pictures and other elements needed for the gui
	 */
	public void loadAssets(Stage stage)
	{
		mainStage = stage;
		mainStage.setTitle("Go game");
		images = new ArrayList<>();
		loadImages();
	}

	public void setupGui(Stage stage, Board currentBoard)
	{
		display = new BorderPane();

		display.setBottom(setupMemory());
		display.setCenter(makeGrid());
		showView();

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
		pane.setMinSize(BUTTON_SIZE*currentBoard.getLenght(), BUTTON_SIZE*currentBoard.getWidth());
		return pane;
	}

	public static void showView()
	{
		showBoard();
		showMemory();
	}
	
	public static void showBoard()
	{
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
				activateButton(thisButton, piece.getPosition());
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

	private static void activateButton(Button button, Position position)
	{
		button.setOnMouseEntered(e -> button.setBackground(images.get(game.isBlack() ? BLACK_IMAGE : WHITE_IMAGE)));
		button.setOnMouseExited(e -> button.setBackground(images.get(EMPTY_IMAGE)));
		button.setOnAction(e -> GoController.placeStone(game, position));
	}

	public static BorderPane setupMemory()
	{
		BorderPane memoryPane = new BorderPane();
		HBox memoryLine = new HBox();
		ScrollPane scroll = new ScrollPane(memoryLine);

		memoryPane.setCenter(scroll);
		memoryPane.setLeft(createMemoryMenu());

		return memoryPane;
	}

	private static VBox createMemoryMenu()
	{
		VBox control = new VBox();

		Button goBack = new Button("Undo");
		goBack.setOnAction(e -> GoController.undo(game));
		control.getChildren().add(goBack);

		return control;

	}

	public static void showMemory()
	{
		//TODO ask will if this is a good idea
		getMemoryLine().clear();
		for(int i = 0; i <= game.getTurn().getTurn();i++)
			addMemory(new Turn(i));
	}
	
	public static void addMemory(Turn turn)
	{
		Button newMemory = new Button("" + turn);
		getMemoryLine().add(newMemory);
		int newTurn = turn.getTurn();
		newMemory.setOnAction(e -> GoController.getBoardAt(game, newTurn));
	}

	public static void removeMemory(int turn)
	{
		getMemoryLine().remove(turn);
	}

	public static ObservableList<Node> getMemoryLine()
	{
		return ((HBox) ((ScrollPane) ((BorderPane) display.getBottom()).getCenter()).getContent()).getChildren();
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
		Image image = new Image(fileName, BUTTON_SIZE, BUTTON_SIZE, true, true);
		BackgroundSize backgroundSize = new BackgroundSize(BUTTON_SIZE, BUTTON_SIZE, true, true, true, false);
		BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, backgroundSize);
		return new Background(background);
	}
}
