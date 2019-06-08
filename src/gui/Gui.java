package gui;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.LALOAD;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import application.Game;
import board.Board;
import boardPiece.BoardPiece;
import boardPiece.BoardPiece.*;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import memory.Memory;

public class Gui extends Application
{
	public static final int BUTTON_SIZE = 50;

	private Stage mainStage;
	private List<Background> images;
	private Game game;

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
		mainStage.setTitle("Test");
		GridPane pane = showBoard(currentBoard);
		mainStage.show();

	}

	public GridPane showBoard(Board currentBoard)
	{
		GridPane board = new GridPane();
		Memory memory = game.getMemory();
		for (int i = 0; i < currentBoard.getLenght(); i++)
		{
			for (int j = 0; j < currentBoard.getWidth(); j++)
			{
				Button temp = new Button();
				temp.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
				board.add(temp, i, j);
			}
		}

		Scene scene = new Scene(board);
		for (int i = 0; i < board.getChildren().size(); i++)
		{
			Button thisButton = (Button) board.getChildren().get(i);
			BoardPiece piece = currentBoard.getBoardPiece(i);

			if (piece instanceof Stone)
				thisButton.setBackground(((Stone) piece).getColor() == Color.BLACK ? images.get(0) : images.get(1));
			else
				thisButton
						.setBackground(((Tile) piece).getStatus() == TileStatus.EMPTY ? images.get(2) : images.get(3));

			if (piece instanceof Tile && ((Tile) piece).getStatus() == TileStatus.EMPTY)
			{
				// TODO add player swap
				thisButton.setOnMouseEntered(e -> thisButton.setBackground(images.get(game.getPlayer() ? 0 : 1)));
				thisButton.setOnMouseExited(e -> thisButton.setBackground(images.get(2)));
				thisButton.setOnAction(new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent event)
					{
						memory.saveBoard(currentBoard);
						boolean suicide = false;
						try
						{
							if (game.getPlayer())
								currentBoard.setStone(Color.BLACK, piece.getXPosition(), piece.getYPosition());
							else
								currentBoard.setStone(Color.WHITE, piece.getXPosition(), piece.getYPosition());
						} catch (SuicideException e)
						{
							suicide = true;
						}
						if (!suicide)
							game.incrementTurn();
						showBoard(currentBoard);

					}
				});
			}
		}
		mainStage.setScene(scene);
		return board;
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
