package board;

import boardPiece.*;
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

	public Board(int lenght, int width) throws ConstructorException, SuicideException
	{
		if (validateSize(lenght, width))
		{
			setSize(lenght+BORDER, width+BORDER);
			instantiateList();
			buildBoard();
		}

		else
			throw new ConstructorException("Invalid size");
	}

	public Board(Board board) throws ConstructorException
	{
		if (board != null)
		{
			setSize(board.lenght, board.width);
			instantiateList();
			for (int i = 0; i < lenght; i++)
			{
				for (int j = 0; j < width; j++)
				{
					pieces.add((i * lenght) + j,board.getBoardPiece(i, j));
				}
			}
		}
		else
			throw new ConstructorException("board is null");
		}

	private void instantiateList()
	{
		pieces = new ArrayList<>();
	}

	private void buildBoard()
	{

		for (int i = 0; i < lenght; i++)
		{
			for (int j = 0; j < width; j++)
			{
				try
				{
					if (isBorder(i, j))
						pieces.add((i * lenght) + j, new Tile(TileStatus.BORDER, new int[] { i, j }, this));
					else
						pieces.add((i * lenght) + j, new Tile(TileStatus.EMPTY, new int[] { i, j }, this));
				} catch (ConstructorException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private boolean isBorder(int xPosition, int yPosition)
	{
		// TODO find a better way to do -1
		return xPosition == 0 || xPosition == lenght - 1 || yPosition == 0 || yPosition == width - 1;
	}

	public static boolean validateSize(int lenght, int width)
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

	public void setTile(TileStatus status, int xPosition, int yPosition)
	{
		try
		{
			pieces.set((xPosition * lenght) + yPosition, new Tile(status, new int[] { xPosition, yPosition }, this));
		} catch (ConstructorException e)
		{
			e.printStackTrace();
		}
	}

	public void setStone(Color color, int xPosition, int yPosition) throws SuicideException
	{
		try
		{
			pieces.set((xPosition * lenght) + yPosition, new Stone(color, new int[] { xPosition, yPosition }, this));
		} catch (ConstructorException e)
		{
			e.printStackTrace();
		}
	}

	public BoardPiece getBoardPiece(int xPosition, int yPosition)
	{
		return pieces.get((xPosition * lenght) + yPosition);
	}

	public BoardPiece getBoardPiece(int index)
	{
		return pieces.get(index);
	}

}
