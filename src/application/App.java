package application;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application
{
	 public static void main(String[] args)
	{
		 //TODO do real app
		 launch(args);
	
		
	}

	@Override
	public void start(Stage arg0) throws Exception
	{
		 try
			{
				new Board(19, 19);
			}
			catch (ConstructorException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (SuicideException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 System.out.println("Exit");
		// TODO Auto-generated method stub
		
	}
}
