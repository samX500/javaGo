package board;

import boardPiece.*;
import control.*;
import exception.ConstructorException;
import exception.SuicideException;
import javafx.scene.layout.GridPane;

import java.util.List;

public class Board
{
	public static final int BORDER = 2;
	private List<List<GoButton>> buttons;
	private int lenght;
	private int width;
	GridPane grid;

	public Board(int lenght, int width)
			throws ConstructorException, SuicideException
	{
		TileStatus currentStatus;
		if (validateSize(lenght, width))
		{
			for (int i = 0; i < lenght + BORDER; i++)
			{
				for (int j = 0; j < width + BORDER; j++)
				{
					if (isBorder(i, j))
						currentStatus = TileStatus.BORDER;
					else
						currentStatus = TileStatus.EMPTY;
					GoButton temp = new GoButton(this, new int[] {i,j},currentStatus);
					grid.add(temp.getButton(), i, j);
				}
			}
		}

	}

	private boolean isBorder(int xPosition, int yPosition)
	{
		return xPosition == 0 || xPosition == lenght + BORDER || yPosition == 0
				|| yPosition == width + BORDER;
	}

	private boolean validateSize(int lenght, int width)
	{
		return lenght > 0 && width > 0;
	}

	public List<List<GoButton>> getButtons()
	{
		return buttons;
	}

	public void setButtons(List<List<GoButton>> buttons)
	{
		this.buttons = buttons;
	}

	public GoButton getGoButton(int xPosition, int yPosition)
	{
		return buttons.get(xPosition).get(yPosition);
	}

	public int getLenght()
	{
		return lenght;
	}

	public void setLenght(int lenght)
	{
		this.lenght = lenght;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

}
