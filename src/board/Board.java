package board;

import boardPiece.*;
import boardPiece.Stone.Color;
import boardPiece.Tile.TileStatus;
import exception.ConstructorException;
import exception.SuicideException;
import javafx.scene.layout.GridPane;
import memory.Move;
import smallStuff.Dimension;
import smallStuff.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Board
{
	public static final int BORDER = 2;
	private List<BoardPiece> pieces;
	private Dimension dimension;

	public Board(int lenght, int width)
	{
		dimension = new Dimension(lenght + BORDER, width + BORDER);
		instantiateList();
		buildBoard();
	}

	public Board(Stack<Move> moveStack, Dimension dimension)
	{
		this(dimension.getLenght(), dimension.getWidth());
		while (!moveStack.isEmpty())
		{
			Move move = moveStack.pop();
			// TODO check the case where the move is null (player passed)
			setStone(move.getColor(), move.getPosition().getX(), move.getPosition().getY());
		}
	}

	private void instantiateList()
	{
		pieces = new ArrayList<>();
	}

	private void buildBoard()
	{

		for (int i = 0; i < dimension.getLenght(); i++)
		{
			for (int j = 0; j < dimension.getWidth(); j++)
			{
				if (isBorder(i, j))
					pieces.add((i * dimension.getLenght()) + j, new Tile(TileStatus.BORDER, i, j));
				else
					pieces.add((i * dimension.getLenght()) + j, new Tile(TileStatus.EMPTY, i, j));
			}
		}
	}

	private boolean isBorder(int xPosition, int yPosition)
	{
		// TODO find a better way to do -1
		return xPosition == 0 || xPosition == dimension.getLenght() - 1 || yPosition == 0
				|| yPosition == dimension.getWidth() - 1;
	}

	public static boolean validateSize(int lenght, int width)
	{
		return lenght > 0 && width > 0;
	}

	public int getLenght()
	{
		return dimension.getLenght();
	}

	public void setLenght(int lenght)
	{
		dimension.setLenght(lenght);
	}

	public int getWidth()
	{
		return dimension.getWidth();
	}

	public void setWidth(int width)
	{
		dimension.setWidth(width);
	}

	public void setSize(int lenght, int width)
	{
		dimension.setLenght(lenght);
		dimension.setWidth(width);
	}

	public void setTile(TileStatus status, int xPosition, int yPosition)
	{
		pieces.set((xPosition * dimension.getLenght()) + yPosition, new Tile(status, xPosition, yPosition));
	}

	public void setStone(Color color, int xPosition, int yPosition)
	{
		Stone stone = new Stone(color, xPosition, yPosition);
		pieces.set((xPosition * dimension.getLenght()) + yPosition, stone);
		stone.activateStone(this);
	}

	public BoardPiece getBoardPiece(int xPosition, int yPosition)
	{
		return pieces.get((xPosition * dimension.getLenght()) + yPosition);
	}

	public BoardPiece getBoardPiece(int index)
	{
		return pieces.get(index);
	}

	public void removeDeadPieces()
	{
		for (BoardPiece stone : pieces)
		{
			if (stone instanceof Stone && ((Stone) stone).isDead())
			{
				((Stone) stone).dies();
				{
					System.out.println("something is wrong");
				this.setTile(TileStatus.EMPTY, stone.getXPosition(), stone.getYPosition());
				}
			}
		}
	}
}
