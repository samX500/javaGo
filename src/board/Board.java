package board;

import boardPiece.*;
import boardPiece.BoardPiece.TileStatus;
import exception.ConstructorException;
import exception.SuicideException;
import gui.Gui;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import memory.Move;
import smallStuff.*;

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
			setBoardPiece(move.getColor(), TileStatus.STONE, move.getPosition().getX(), move.getPosition().getY());

			if (i > 1)
			{
				KOBoard[i] = clone(this);
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
				BoardPiece piece = board.getBoardPiece((i * board.getLenght()) + j);

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
					pieces.add((i * dimension.getLenght()) + j,
							new BoardPiece(i, j, Color.colorless, TileStatus.BORDER));
				else
					pieces.add((i * dimension.getLenght()) + j,
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
		pieces.set((xPosition * dimension.getLenght()) + yPosition,
				new BoardPiece(xPosition, yPosition, color, status));
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

	public boolean checkDeadPiece(Player[] players, Position position, Board koBoard)
	{
		List<BoardPiece> victims = new ArrayList<>();
		BoardPiece stone = getBoardPiece(position);

		if (stoneIsDead(stone, victims))
			return checkKO(stone, players, koBoard);

		for (int i = 0; i < Direction.values().length; i++)
		{
			BoardPiece piece = getNeighbours(position, Direction.values()[i]);
			if (stoneIsDead(piece, victims))
				for (BoardPiece victim : victims)
					killStone(victim, players);
		}

		return false;
	}

	private boolean checkKO(BoardPiece stone, Player[] players, Board koBoard)
	{
		List<BoardPiece> victims = new ArrayList<>();

		for (int i = 0; i < Direction.values().length; i++)
		{
			BoardPiece piece = getNeighbours(stone.getPosition(), Direction.values()[i]);
			if (stoneIsDead(piece, victims))
			{
				Board testEqual = clone(this);
				for (BoardPiece victim : victims)
					testEqual.setBoardPiece(Color.colorless, TileStatus.EMPTY, victim.getXPosition(),
							victim.getYPosition());

				if (!testEqual.equals(koBoard))
				{
					for (BoardPiece victim : victims)
						killStone(victim, players);
					return false;
				}
			}
		}

		killStone(stone, players);

		return true;
	}

	private boolean stoneIsDead(BoardPiece stone, List<BoardPiece> victims)
	{
		List<Position> positionCheck = new ArrayList<>();
		for (int i = 0; i < Direction.values().length; i++)
		{
			victims.add(stone);
			boolean hasLiberties = hasLiberties(stone.getColor(),
					getNeighbours(stone.getPosition(), Direction.values()[i]), positionCheck, victims);
			if (hasLiberties)
			{
				victims.clear();
				return false;
			}

		}
		return true;
	}

	private boolean hasLiberties(Color color, BoardPiece piece, List<Position> positionCheck, List<BoardPiece> victims)
	{
		if (!positionCheck.contains(piece.getPosition()))
		{
			positionCheck.add(piece.getPosition());

			if (piece.getStatus() == TileStatus.EMPTY)
				return true;
			else if (piece.getStatus() == TileStatus.STONE && piece.getColor() == color)
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
		boolean[][] positionCheck = new boolean[][] { staticList.instantiateBooleanList(false, pieces.size()),
				staticList.instantiateBooleanList(false, pieces.size()) };

		for (BoardPiece piece : pieces)
		{
			if (piece.getStatus() == TileStatus.STONE)
				for (int i = 0; i < Direction.values().length; i++)
					checkTerritory(getNeighbours(piece.getPosition(), Direction.values()[i]), (piece).getColor(),
							positionCheck);

		}

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

		if (piece.getStatus() == TileStatus.EMPTY
				&& !positionCheck[color.getValue()][piece.getXPosition() * dimension.getLenght()
						+ piece.getYPosition()])
		{
			if (piece.getColor() == Color.colorless)
				piece.setColor(color);
			else if (piece.getColor() != color)
				piece.setColor(Color.colorless);

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
				return false;
		}

		return true;
	}

	public String toString()
	{
		String string = "";

		for (int i = 0; i < pieces.size(); i++)
			string += pieces.get(i) + "\n";

		return string;
	}

}
