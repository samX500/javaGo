package application;

import java.util.List;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;
import gui.Gui;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application
{
	
	public static final int BUTTON_SIZE = 50;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		//TODO code app
		Board board = new Board(19, 19);
		Gui.setupGui();
		Gui.showView(stage,board);
		
	}

}
