package gui;

import javafx.scene.control.ScrollPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.xml.internal.ws.api.pipe.PipeCloner;

import application.Game;
import board.Board;
import boardPiece.BoardPiece;
import control.GoController;
import control.TurnButton;
import boardPiece.BoardPiece.TileStatus;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import smallStuff.*;

public class Gui extends Application
{
	public static final int BUTTON_SIZE = 50;

	public static final int BLACK_IMAGE = 0;
	public static final int WHITE_IMAGE = 1;
	public static final int EMPTY_IMAGE = 2;
	public static final int BORDER_IMAGE = 3;
	public static final int EMPTY_BLACK_IMAGE = 4;
	public static final int EMPTY_WHITE_IMAGE = 5;
	public static final int FADED_BLACK_EMPTY_IMAGE = 6;
	public static final int FADED_BLACK_BLACK_IMAGE = 7;
	public static final int FADED_BLACK_WHITE_IMAGE = 8;
	public static final int FADED_WHITE_EMPTY_IMAGE = 9;
	public static final int FADED_WHITE_BLACK_IMAGE = 10;
	public static final int FADED_WHITE_WHITE_IMAGE = 11;

	public static final int CAPTURE = 1;
	public static final int TERRITORY = 2;
	public static final int KOMI = 3;
	public static final int TOTAL = 4;

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
		display.setRight(pointChart());
		showView();

		Scene scene = new Scene(display);
		// TODO figure out the min dimension
		mainStage.setMinWidth(BUTTON_SIZE * game.getBoard().getLenght());
		mainStage.setMinHeight(BUTTON_SIZE * game.getBoard().getLenght());
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
		pane.setMinSize(BUTTON_SIZE * currentBoard.getLenght(), BUTTON_SIZE * currentBoard.getWidth());
		return pane;
	}

	public static void showView()
	{
		showBoard();
		showMemory();
		showPoints();
	}

	public static void showBoard()
	{
		Board currentBoard = game.getBoard();
		GridPane pane = (GridPane) display.getCenter();

		for (int i = 0; i < pane.getChildren().size(); i++)
		{
			Button thisButton = (Button) pane.getChildren().get(i);
			BoardPiece piece = currentBoard.getBoardPiece(i);

			if (piece.getStatus() == TileStatus.STONE)
				thisButton.setBackground(
						(piece.getColor() == Color.black ? images.get(BLACK_IMAGE) : images.get(WHITE_IMAGE)));
			else
				thisButton.setBackground(
						piece.getStatus() == TileStatus.EMPTY ? images.get(EMPTY_IMAGE) : images.get(BORDER_IMAGE));

			if (piece.getStatus() == TileStatus.EMPTY)
				activateButton(thisButton, piece.getPosition());
			else
				disableButton(thisButton);

		}
	}

	public static void showTerritory()
	{
		Board currentBoard = game.getBoard();
		GridPane pane = (GridPane) display.getCenter();

		for (int i = 0; i < pane.getChildren().size(); i++)
		{
			Button thisButton = (Button) pane.getChildren().get(i);
			BoardPiece piece = currentBoard.getBoardPiece(i);

			if (piece.getStatus() == TileStatus.STONE)
				thisButton.setBackground(
						(piece.getColor() == Color.black ? images.get(BLACK_IMAGE) : images.get(WHITE_IMAGE)));
			else if (piece.getColor() != Color.colorless)
				thisButton.setBackground(piece.getColor() == Color.black ? images.get(EMPTY_BLACK_IMAGE)
						: images.get(EMPTY_WHITE_IMAGE));
			else
				thisButton.setBackground(
						piece.getStatus() == TileStatus.EMPTY ? images.get(EMPTY_IMAGE) : images.get(BORDER_IMAGE));

			if (piece.getStatus() == TileStatus.EMPTY)
				activateTerritoryButton(thisButton, piece);
			else
				disableButton(thisButton);

		}
	}

	public static void showEndGame(HashMap<Position, Color> fadedStone)
	{
		Board currentBoard = game.getBoard();
		GridPane pane = (GridPane) display.getCenter();

		for (int i = 0; i < pane.getChildren().size(); i++)
		{
			Button thisButton = (Button) pane.getChildren().get(i);
			BoardPiece piece = currentBoard.getBoardPiece(i);
			if (piece.getStatus() == TileStatus.STONE)
				thisButton.setBackground(
						(piece.getColor() == Color.black ? images.get(BLACK_IMAGE) : images.get(WHITE_IMAGE)));
			else if (piece.getStatus() == TileStatus.BORDER)
				thisButton.setBackground(images.get(BORDER_IMAGE));
			else if (fadedStone.containsKey(piece.getPosition()))
				switch (piece.getColor())
				{
				case colorless:
					thisButton.setBackground(
							fadedStone.get(piece.getPosition()) == Color.black ? images.get(FADED_BLACK_EMPTY_IMAGE)
									: images.get(FADED_WHITE_EMPTY_IMAGE));
					break;
				case black:
					thisButton.setBackground(
							fadedStone.get(piece.getPosition()) == Color.black ? images.get(FADED_BLACK_BLACK_IMAGE)
									: images.get(FADED_WHITE_BLACK_IMAGE));
					break;

				case white:
					thisButton.setBackground(
							fadedStone.get(piece.getPosition()) == Color.black ? images.get(FADED_BLACK_WHITE_IMAGE)
									: images.get(FADED_WHITE_WHITE_IMAGE));
					break;
				}
			else if (piece.getColor() != Color.colorless)
				thisButton.setBackground(piece.getColor() == Color.black ? images.get(EMPTY_BLACK_IMAGE)
						: images.get(EMPTY_WHITE_IMAGE));
			else
				thisButton.setBackground(images.get(EMPTY_IMAGE));

			if (piece.getStatus() == TileStatus.STONE || fadedStone.containsKey(piece.getPosition()))
				activateEndGameButton(thisButton, piece.getPosition(), fadedStone);
			else
				disableButton(thisButton);
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

		boolean isBlack = TurnButton.isActive() ? TurnButton.getTurn() % 2 == 0 : game.isBlack();

		button.setOnMouseEntered(e -> button.setBackground(images.get(isBlack ? BLACK_IMAGE : WHITE_IMAGE)));
		button.setOnMouseExited(e -> button.setBackground(images.get(EMPTY_IMAGE)));
		button.setOnAction(e -> GoController.placeStone(game, position));
	}

	private static void activateTerritoryButton(Button button, BoardPiece piece)
	{
		boolean isBlack = TurnButton.isActive() ? TurnButton.getTurn() % 2 == 0 : game.isBlack();

		button.setOnMouseEntered(e -> button.setBackground(images.get(isBlack ? BLACK_IMAGE : WHITE_IMAGE)));
		button.setOnMouseExited(e -> button.setBackground(piece.getColor() == Color.colorless ? images.get(EMPTY_IMAGE)
				: piece.getColor() == Color.black ? images.get(EMPTY_BLACK_IMAGE) : images.get(EMPTY_WHITE_IMAGE)));
		button.setOnAction(e -> GoController.placeStone(game, piece.getPosition()));
	}

	private static void activateEndGameButton(Button button, Position position, HashMap<Position, Color> fadedStone)
	{
		button.setOnAction(e -> GoController.fadeStone(game, position, fadedStone));
	}

	public static ScrollPane setupMemory()
	{
		HBox memoryLine = new HBox();
		ScrollPane scroll = new ScrollPane(memoryLine);

		return scroll;
	}

	public static void showMemory()
	{
		getMemoryLine().clear();
		for (int i = 0; i <= game.getTurn().getTurn(); i++)
			addMemory(new Turn(i));
	}

	public static void addMemory(Turn turn)
	{
		Button newMemory = new Button("" + turn);
		newMemory.setPrefHeight(BUTTON_SIZE);
		getMemoryLine().add(newMemory);
		int newTurn = turn.getTurn();
		newMemory.setOnAction(e -> GoController.getBoardAt(game, newTurn));
	}

	public static ObservableList<Node> getMemoryLine()
	{
		return ((HBox) ((ScrollPane) display.getBottom()).getContent()).getChildren();
	}

	public static BorderPane pointChart()
	{

		BorderPane pointChart = new BorderPane();
		HBox control = new HBox();

		Button undo = new Button("Undo");
		Button pass = new Button("Pass");
		Button showTerritory = new Button("Show territory");
		Button confirmEnd = new Button("");

		undo.setPrefHeight(BUTTON_SIZE);
		undo.setMinWidth(BUTTON_SIZE);
		undo.setOnAction(e -> GoController.undo(game));
		
		pass.setPrefHeight(BUTTON_SIZE);
		pass.setMinWidth(BUTTON_SIZE);
		pass.setOnAction(e -> GoController.pass(game));

		showTerritory.setPrefHeight(BUTTON_SIZE);
		showTerritory.setMinWidth(BUTTON_SIZE);
		showTerritory.setOnAction(e -> GoController.showTerritory(game));

		confirmEnd.setPrefHeight(BUTTON_SIZE);
		confirmEnd.setMinWidth(BUTTON_SIZE);
		confirmEnd.setOnAction(null);

		control.getChildren().addAll(undo, pass, showTerritory, confirmEnd);

		pointChart.setCenter(pointTable());
		pointChart.setBottom(control);

		return pointChart;
	}

	public static void showPoints()
	{
		HBox pointTable = getPointTable();

		ObservableList<Node> blackPoints = ((VBox) pointTable.getChildren().get(0)).getChildren();
		ObservableList<Node> whitePoints = ((VBox) pointTable.getChildren().get(1)).getChildren();

		int[] territory = game.getBoard().countTerritory();

		((Label) blackPoints.get(CAPTURE)).setText("Capture: " + game.getPlayer1().getCapture());
		((Label) blackPoints.get(TERRITORY)).setText("Territory: " + territory[Color.black.getValue()]);
		((Label) blackPoints.get(KOMI)).setText("Komi: " + game.getBlackKomi());
		((Label) blackPoints.get(TOTAL)).setText(
				"Total: " + (game.getPlayer1().getCapture() + territory[Color.black.getValue()] + game.getBlackKomi()));

		((Label) whitePoints.get(CAPTURE)).setText("Capture: " + game.getPlayer2().getCapture());
		((Label) whitePoints.get(TERRITORY)).setText("Territory: " + territory[Color.white.getValue()]);
		((Label) whitePoints.get(KOMI)).setText("Komi: " + game.getWhiteKomi());
		((Label) whitePoints.get(TOTAL)).setText(
				"Total: " + (game.getPlayer2().getCapture() + territory[Color.white.getValue()] + game.getWhiteKomi()));

	}

	private static HBox pointTable()
	{
		HBox points = new HBox();
		VBox black = new VBox();
		VBox white = new VBox();

		Label blackLabel = getLabel("Black", BUTTON_SIZE);
		Label blackCapture = getLabel("", BUTTON_SIZE);
		Label blackTerritory = getLabel("", BUTTON_SIZE);
		Label blackKomi = getLabel("", BUTTON_SIZE);
		Label blackTotal = getLabel("", BUTTON_SIZE);

		Label whiteLabel = getLabel("White", BUTTON_SIZE);
		Label whiteCapture = getLabel("", BUTTON_SIZE);
		Label whiteTerritory = getLabel("", BUTTON_SIZE);
		Label whiteKomi = getLabel("", BUTTON_SIZE);
		Label whiteTotal = getLabel("", BUTTON_SIZE);

		black.getChildren().addAll(blackLabel, blackCapture, blackTerritory, blackKomi, blackTotal);
		white.getChildren().addAll(whiteLabel, whiteCapture, whiteTerritory, whiteKomi, whiteTotal);

		points.getChildren().addAll(black, white);
		points.setSpacing(BUTTON_SIZE);
		points.setPadding(new Insets(BUTTON_SIZE));

		return points;
	}

	public static void setEndGameControl()
	{
		ObservableList<Node> control = getControl();

		setButton((Button) control.get(1), null, "");
		setButton((Button) control.get(2), null, "");
		setButton((Button) control.get(3), e -> GoController.confirmEnd(game), "Both player confirmed dead group");
	}

	public static void removeEndGameControl()
	{
		ObservableList<Node> control = getControl();

		setButton((Button) control.get(1), e -> GoController.pass(game), "Pass");
		setButton((Button) control.get(2), e -> GoController.showTerritory(game), "Show territory");
		setButton((Button) control.get(3), null, "");
	}

	public static void setButton(Button button, EventHandler<ActionEvent> event, String title)
	{
		button.setOnAction(event);
		button.setText(title);
	}

	private static Label getLabel(String message, int size)
	{
		Label label = new Label(message);
		label.setFont(new Font(size));
		return label;
	}

	private static HBox getPointTable()
	{
		return (HBox) ((BorderPane) display.getRight()).getCenter();
	}

	private static ObservableList<Node> getControl()
	{
		return ((HBox) ((BorderPane) display.getRight()).getBottom()).getChildren();
	}

	public static void winMessage(String title, String message)
	{
		Alert winner = new Alert(AlertType.INFORMATION);
		winner.setTitle(title);
		winner.setContentText(message);
		
		winner.showAndWait();
		
		mainStage.close();
	}
	
	private void loadImages()
	{
		images.add(loadBackground("goBlack.png"));
		images.add(loadBackground("goWhite.png"));
		images.add(loadBackground("emptyTile.png"));
		images.add(loadBackground("borderTile.jpg"));
		images.add(loadBackground("blackEmpty.png"));
		images.add(loadBackground("whiteEmpty.png"));
		images.add(loadBackground("fadedBlackEmpty.png"));
		images.add(loadBackground("fadedBlackBlack.png"));
		images.add(loadBackground("fadedBlackWhite.png"));
		images.add(loadBackground("fadedWhiteEmpty.png"));
		images.add(loadBackground("fadedWhiteBlack.png"));
		images.add(loadBackground("fadedWhiteWhite.png"));
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
