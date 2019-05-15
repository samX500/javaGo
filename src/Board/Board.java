package Board;

import boardPiece.*;
import control.*;
import java.util.List;

public class Board
{
	private List<List<GoButton>> buttons;
	private int lenght;
	private int width;

	public Board()
	{
		// TODO Auto-generated constructor stub
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
