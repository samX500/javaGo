package board;

import boardPiece.*;
import boardPiece.BoardPiece.TileStatus;
import memory.Move;
import smallStuff.*;
import java.util.ArrayList;
import java.util.HashMap;
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
			if (move.getPosition() != null)
				setBoardPiece(move.getColor(), TileStatus.STONE, move.getPosition().getX(), move.getPosition().getY());

			if (i > 1)
			{
				KOBoard[i] = clone(this);

				if (move.getPosition() != null)
					this.checkDeadPiece(players, move.getPosition(), KOBoard[i - 2]);
			} else
				KOBoard[i] = null;
			i++;
		}

	}

	private static Board clone(Board board)
	{
		Board clone = new Board(board.getLenght() - BORDER, board.getWidth() - BORDER);

		for (int i = 0; i < board.getLenght(); i++)
		{
			for (int j = 0; j < board.getWidth(); j++)
			{
				BoardPiece piece = board.getBoardPiece((i * board.getWidth()) + j);

				clone.setBoardPiece(piece.getColor(), piece.getStatus(), i, j);
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
					pieces.add((i * dimension.getWidth()) + j,
							new BoardPiece(i, j, Color.colorless, TileStatus.BORDER));
				else
					pieces.add((i * dimension.getWidth()) + j,
							new BoardPiece(i, j, Color.colorless, TileStatus.EMPTY));
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

	public int getArea()
	{
		return dimension.getArea();
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

	public void setBoardPiece(Color color, TileStatus status, int xPosition, int yPosition)
	{
		pieces.set((xPosition * dimension.getWidth()) + yPosition,
				new BoardPiece(xPosition, yPosition, color, status));
	}

	public BoardPiece getBoardPiece(int xPosition, int yPosition)
	{
		return pieces.get((xPosition * dimension.getWidth()) + yPosition);
	}

	public BoardPiece getBoardPiece(Position position)
	{
		return pieces.get((position.getX() * dimension.getWidth()) + position.getY());
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

	public boolean checkDeadPiece(Player[] players, Position position, Board koBoard)
	{
		List<BoardPiece> victims = new ArrayList<>();
		BoardPiece stone = getBoardPiece(position);

		if (stone.isStone() && stoneIsDead(stone, victims))
			return checkKO(stone, players, koBoard);

		for (int i = 0; i < Direction.values().length; i++)
		{
			BoardPiece piece = getNeighbours(position, Direction.values()[i]);
			if (piece.isStone() && stoneIsDead(piece, victims))
				for (BoardPiece victim : victims)
					killStone(victim, players);
		}

		return false;
	}

	private boolean checkKO(BoardPiece stone, Player[] players, Board koBoard)
	{
		boolean suicide = true;
		List<BoardPiece> victims = new ArrayList<>();

		for (int i = 0; i < Direction.values().length; i++)
		{
			BoardPiece piece = getNeighbours(stone.getPosition(), Direction.values()[i]);
			if (piece.isStone() && piece.getColor() != stone.getColor() && stoneIsDead(piece, victims))
			{
				Board testEqual = clone(this);
				for (BoardPiece victim : victims)
					testEqual.setBoardPiece(Color.colorless, TileStatus.EMPTY, victim.getXPosition(),
							victim.getYPosition());

				if (!testEqual.equals(koBoard))
				{
					for (BoardPiece victim : victims)
						killStone(victim, players);
					victims.clear();
					suicide = false;
				}
			}
		}

		if (!suicide)
			return false;

		// Sends a null player because suicide shouldn't count point
		killStone(stone, null);

		return true;
	}

	private boolean stoneIsDead(BoardPiece stone, List<BoardPiece> victims)
	{
		List<Position> positionCheck = new ArrayList<>();
		positionCheck.add(stone.getPosition());
		for (int i = 0; i < Direction.values().length; i++)
		{
			boolean hasLiberties = hasLiberties(stone.getColor(),
					getNeighbours(stone.getPosition(), Direction.values()[i]), positionCheck, victims);
			if (hasLiberties)
			{
				victims.clear();
				return false;
			}
		}
		victims.add(stone);
		return true;
	}

	private boolean hasLiberties(Color color, BoardPiece piece, List<Position> positionCheck, List<BoardPiece> victims)
	{
		if (!positionCheck.contains(piece.getPosition()))
		{
			positionCheck.add(piece.getPosition());

			if (piece.getStatus() == TileStatus.EMPTY)
				return true;
			else if (piece.isStone() && piece.getColor() == color)
			{
				victims.add(piece);
				for (int i = 0; i < Direction.values().length; i++)
				{
					boolean hasLiberties = hasLiberties(piece.getColor(),
							getNeighbours(piece.getPosition(), Direction.values()[i]), positionCheck, victims);
					if (hasLiberties)
						return true;
				}
			}
		}
		return false;
	}

	private void killStone(BoardPiece stone, Player[] players)
	{
		if (players != null && stone.getColor() != Color.colorless)
			players[stone.getColor().getOpposite()].addCapture();
		this.setBoardPiece(Color.colorless, TileStatus.EMPTY, stone.getXPosition(), stone.getYPosition());
	}

	public int[] countTerritory()
	{
		boolean isStone = false;
		boolean[][] positionCheck = new boolean[][] { StaticList.instantiateBooleanList(false, pieces.size()),
				StaticList.instantiateBooleanList(false, pieces.size()) };

		for (BoardPiece piece : pieces)
			if (piece.isStone())
			{
				isStone = true;
				for (int i = 0; i < Direction.values().length; i++)
					checkTerritory(getNeighbours(piece.getPosition(), Direction.values()[i]), piece.getColor(),
							positionCheck);
			}
		if(!isStone)
			for (BoardPiece piece : pieces)
				if(piece.getStatus()==TileStatus.EMPTY)
				piece.setColor(Color.colorless);
					
		return countPoint();
	}

	private int[] countPoint()
	{
		int[] count = new int[] { 0, 0 };

		for (BoardPiece piece : pieces)
		{
			if (piece.getStatus() == TileStatus.EMPTY && piece.getColor() != Color.colorless)
				count[piece.getColor().getValue()]++;
		}
		return count;
	}

	private void checkTerritory(BoardPiece piece, Color color, boolean[][] positionCheck)
	{
		// color should never be colorless, if it were to be colorless an
		// arrayOutofBoundException would occur
		if (!positionCheck[color.getValue()][piece.getXPosition() * dimension.getLenght() + piece.getYPosition()])
		{
			positionCheck[color.getValue()][piece.getXPosition() * dimension.getLenght() + piece.getYPosition()] = true;

			if (piece.getStatus() == TileStatus.EMPTY)
			{
				if (piece.getColor() == Color.colorless)
					piece.setColor(color);
				else if (piece.getColor() != color)
				{
					positionCheck[color.getOpposite()][piece.getXPosition() * dimension.getLenght()
							+ piece.getYPosition()] = true;
					piece.setColor(Color.colorless);
				}
				for (int i = 0; i < Direction.values().length; i++)
					checkTerritory(getNeighbours(piece.getPosition(), Direction.values()[i]), color, positionCheck);
			}

		}
	}

	public void floodFade(Color color, Position position, HashMap<Position, Color> fadedStone,
			ArrayList<Position> positionCheck, Player[] players)
	{
		BoardPiece piece = getBoardPiece(position);
		if (!positionCheck.contains(position))
		{
			positionCheck.add(position);
			if (piece.getStatus() == TileStatus.STONE && piece.getColor() == color)
			{
				fadedStone.put(position, color);
				killStone(piece, players);

				for (int i = 0; i < Direction.values().length; i++)
					floodFade(color, getNeighbours(position, Direction.values()[i]).getPosition(), fadedStone,
							positionCheck, players);
			}
		}
	}

	public void floodUnfade(Color color, Position position, HashMap<Position, Color> fadedStone,
			ArrayList<Position> positionCheck, Player[] players)
	{
		if (!positionCheck.contains(position))
		{
			positionCheck.add(position);

			if (fadedStone.containsKey(position) && fadedStone.get(position) == color)
			{
				setBoardPiece(fadedStone.get(position), TileStatus.STONE, position.getX(), position.getY());
				players[fadedStone.get(position).getOpposite()].removeCapture();

				for (int i = 0; i < Direction.values().length; i++)
					floodUnfade(color, getNeighbours(position, Direction.values()[i]).getPosition(), fadedStone,
							positionCheck, players);
			}
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
				return false;
			}
		}
		return true;
	}

	public String toString()
	{
		String string = "";

		for (int i = 0; i < pieces.size(); i++)
			string += pieces.get(i).getStatus() == TileStatus.STONE ? pieces.get(i) + "\n" : "";

		return string;
	}

}
