package board;

import boardPiece.*;
import control.*;
import exception.ConstructorException;
import exception.SuicideException;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Board
{
	public static final int BORDER = 2;
	private List<BoardPiece> pieces;
	private int lenght;
	private int width;
	GridPane grid;

	public Board(int lenght, int width) throws ConstructorException, SuicideException
	{
		if (validateSize(lenght, width))
		{
			setSize(lenght, width);
			instantiateList();
			buildBoard();
		}

		else
			throw new ConstructorException("Invalid size");
	}

	private void instantiateList()
	{
		pieces = new ArrayList<>();
	}

	private void buildBoard()
	{
		for (int i = 0; i < lenght + BORDER; i++)
		{
			for (int j = 0; j < width + BORDER; j++)
			{
				if (isBorder(i, j))
				{
					setBoardPiece(TileStatus.BORDER, i, j);
					System.out.println("B " + i + " " + j);
				} else
				{
					setBoardPiece(TileStatus.EMPTY, i, j);
					System.out.println("E " + i + " " + j);
				}
			}
		}
	}

	private boolean isBorder(int xPosition, int yPosition)
	{
		// TODO find a better way to do -1
		return xPosition == 0 || xPosition == lenght + BORDER - 1 || yPosition == 0 || yPosition == width + BORDER - 1;
	}

	private boolean validateSize(int lenght, int width)
	{
		return lenght > 0 && width > 0;
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

	public void setSize(int lenght, int width)
	{
		setLenght(lenght);
		setWidth(width);
	}

	public void setBoardPiece(TileStatus status, int xPosition, int yPosition)
	{
		try
		{
			pieces.add((xPosition * lenght) + yPosition,
					new BoardPiece(this, new int[] { xPosition, yPosition }, status));
		} catch (ConstructorException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SuicideException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resetBoardPiece(BoardPiece newPiece, int xPosition, int yPosition)
	{
		pieces.set((xPosition * lenght) + yPosition, newPiece);
	}

	public BoardPiece getBoardPiece(int xPosition, int yPosition)
	{
		// TODO I am sure this won't work
		return pieces.get((xPosition * lenght) + yPosition);
	}

}
