package board;

import boardPiece.*;
import boardPiece.Tile.TileStatus;
import exception.ConstructorException;
import exception.SuicideException;
import javafx.scene.layout.GridPane;
import memory.Move;
import smallStuff.*;
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

	/**
	 * Creates an empty board
	 * 
	 * @param lenght lenght of the board
	 * @param width  width of the board
	 */
	public Board(int lenght, int width)
	{
		dimension = new Dimension(lenght + BORDER, width + BORDER);
		instantiateList();
		buildBoard();
	}

	/**
	 * Creates a board from a stack of move.
	 * 
	 * @param moveStack stack containing move played
	 * @param dimension dimension of the board
	 * @param players   players playing the game
	 * @throws CloneNotSupportedException
	 */
	public Board(Stack<Move> moveStack, Dimension dimension, Player[] players)
	{
		this(dimension.getLenght(), dimension.getWidth());

		Board[] KOBoard = new Board[moveStack.size() + 1];
		int i = 0;

		while (!moveStack.isEmpty())
		{
			Move move = moveStack.pop();
			// TODO check the case where the move is null (player passed)
			setStone(move.getColor(), move.getPosition().getX(), move.getPosition().getY());

			if (i > 1)
			{
				KOBoard[i] = clone(this);
				this.removeDeadPieces(players, move.getPosition(), KOBoard[i - 2]);
			} else
				KOBoard[i] = null;
			i++;
		}

	}

	private static Board clone(Board board)
	{
		Board clone = new Board(board.getLenght(), board.getWidth());

		for (int i = 0; i < board.getLenght(); i++)
		{
			for (int j = 0; j < board.getWidth(); j++)
			{
				BoardPiece piece = board.getBoardPiece((i * board.getLenght()) + j);

				if (piece instanceof Tile)
					clone.setTile(((Tile) piece).getStatus(), i, j);
				else if (piece instanceof Stone)
					clone.setStone(((Stone) piece).getColor(), i, j);
			}
		}

		return clone;
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

	public Dimension getDimension()
	{
		return dimension;
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

	public BoardPiece getBoardPiece(Position position)
	{
		return pieces.get((position.getX() * dimension.getLenght()) + position.getY());
	}

	public BoardPiece getBoardPiece(int index)
	{
		return pieces.get(index);
	}

	public BoardPiece getNeighbours(Position position, Direction direction)
	{
		switch (direction)
		{
		case Left:
			return getBoardPiece(position.getX() - 1, position.getY());
		case Right:
			return getBoardPiece(position.getX() + 1, position.getY());
		case Top:
			return getBoardPiece(position.getX(), position.getY() - 1);
		case Bottom:
			return getBoardPiece(position.getX(), position.getY() + 1);
		}

		return null;
	}

	public boolean removeDeadPieces(Player[] players, Position position, Board board)
	{
		Stone thisStone = position == null ? null : (Stone) getBoardPiece(position);
		if (thisStone != null && thisStone.isDead())
			return checkKO(thisStone, players, board);

		else
			for (BoardPiece stone : pieces)
				if (stone instanceof Stone && ((Stone) stone).isDead())
					killStone((Stone) stone, players);

		return false;
	}

	private boolean checkKO(Stone stone, Player[] players, Board board)
	{
		boolean suicide = true;

		for (BoardPiece piece : stone.getNeighbours())
		{
			if (piece instanceof Stone && ((Stone) piece).getColor() != stone.getColor() && ((Stone) piece).isDead())
			{
				if (!this.equals(board))
				{
					killStone((Stone) piece, players);
					suicide = false;
				}
			}
		}

		if (suicide)
		{
			((Stone) stone).dies();
			this.setTile(TileStatus.EMPTY, stone.getXPosition(), stone.getYPosition());
		}

		return suicide;
	}

	private void killStone(Stone stone, Player[] players)
	{
		players[((Stone) stone).getColor().getOpposite()].addCapture();
		((Stone) stone).dies();
		this.setTile(TileStatus.EMPTY, stone.getXPosition(), stone.getYPosition());
	}

	public int[] countTerritory()
	{
		boolean[][] positionCheck = new boolean[][] { staticList.instantiateBooleanList(false, pieces.size()),
				staticList.instantiateBooleanList(false, pieces.size()) };

		for (BoardPiece piece : pieces)
		{
			if (piece instanceof Stone)
			{
				checkTerritory(getNeighbours(piece.getPosition(), Direction.Left), ((Stone) piece).getColor(),
						positionCheck);
				checkTerritory(getNeighbours(piece.getPosition(), Direction.Right), ((Stone) piece).getColor(),
						positionCheck);
				checkTerritory(getNeighbours(piece.getPosition(), Direction.Top), ((Stone) piece).getColor(),
						positionCheck);
				checkTerritory(getNeighbours(piece.getPosition(), Direction.Bottom), ((Stone) piece).getColor(),
						positionCheck);
			}
		}

		return countPoint();
	}

	private int[] countPoint()
	{
		int blackCount = 0;
		int whiteCount = 0;
		for (BoardPiece piece : pieces)
		{
			if (piece instanceof Tile)
			{
				if (((Tile) piece).getColor() == Color.black)
					blackCount++;
				else if (((Tile) piece).getColor() == Color.white)
					whiteCount++;
			}
		}
		return new int[] { blackCount, whiteCount };
	}

	public void checkTerritory(BoardPiece piece, Color color, boolean[][] positionCheck)
	{
		// color should never be colorless, if it were to be colorless an
		// arrayOutofBoundException would occur

		if (piece instanceof Tile && ((Tile) piece).getStatus() == TileStatus.EMPTY
				&& !positionCheck[color.getValue()][piece.getXPosition() * dimension.getLenght()
						+ piece.getYPosition()])
		{
			if (((Tile) piece).getColor() == Color.colorless)
				((Tile) piece).setColor(color);
			else if (((Tile) piece).getColor() != color)
				((Tile) piece).setColor(Color.colorless);

			positionCheck[color.getValue()][piece.getXPosition() * dimension.getLenght() + piece.getYPosition()] = true;

			checkTerritory(getNeighbours(piece.getPosition(), Direction.Left), color, positionCheck);
			checkTerritory(getNeighbours(piece.getPosition(), Direction.Right), color, positionCheck);
			checkTerritory(getNeighbours(piece.getPosition(), Direction.Top), color, positionCheck);
			checkTerritory(getNeighbours(piece.getPosition(), Direction.Bottom), color, positionCheck);
		}
	}

	public boolean equals(Board board)
	{
		if (!this.getDimension().equal(board.getDimension()))
			return false;

		for (int i = 0; i < pieces.size(); i++)
		{
			if (!this.getBoardPiece(i).equals(board.getBoardPiece(i)))
			{
				System.out.println(this.getBoardPiece(i)+" "+board.getBoardPiece(i));
				return false;
			}
				
		}

		return true;
	}
}
