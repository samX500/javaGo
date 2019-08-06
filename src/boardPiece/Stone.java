package boardPiece;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import boardPiece.Tile.TileStatus;
import exception.ConstructorException;
import exception.SuicideException;
import interfacePack.Killable;
import smallStuff.Color;
import smallStuff.Direction;
import smallStuff.Position;

public class Stone extends BoardPiece implements Killable
{
	private StoneGroup currentGroup;
	private Color color;
	List<Position> liberties;
	private BoardPiece[] neighbours;

	public Stone(Color color, int x, int y)
	{
		super(x, y);
		liberties = new ArrayList<>();
		setColor(color);
	}

	public void activateStone(Board board)
	{
		generateNeighbours(board);
		generateLiberties();
		updateNeighbours();
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public BoardPiece[] getNeighbours()
	{
		return neighbours;
	}

	public BoardPiece getNeighbours(Direction direction)
	{
		switch (direction)
		{
		case Right:
			return neighbours[0];
		case Left:
			return neighbours[1];
		case Top:
			return neighbours[2];
		case Bottom:
			return neighbours[3];
		}
		return null;
	}

	private void generateNeighbours(Board board)
	{
		neighbours = new BoardPiece[4];
		neighbours[0] = board.getNeighbours(getPosition(), Direction.Right);
		neighbours[1] = board.getNeighbours(getPosition(), Direction.Left);
		neighbours[2] = board.getNeighbours(getPosition(), Direction.Top);
		neighbours[3] = board.getNeighbours(getPosition(), Direction.Bottom);
	}

	private void addNeighbours(Stone newNeighbours)
	{
		int index = validateNeighbours(newNeighbours.getXPosition(), newNeighbours.getYPosition());
		if (index >= 0)
		{
			neighbours[index] = newNeighbours;
			removeLiberties(newNeighbours.getPosition());
		}
	}

	private int validateNeighbours(int x, int y)
	{
		// TODO modify this a bit
		if (x == getXPosition() + 1 && y == getYPosition())
			return 0;
		else if (x == getXPosition() - 1 && y == getYPosition())
			return 1;
		else if (x == getXPosition() && y == getYPosition() + 1)
			return 2;
		else if (x == getXPosition() && y == getYPosition() - 1)
			return 3;
		return -1;
	}

	@Override
	public List<Position> getLiberties()
	{
		return liberties;
	}

	@Override
	public void setALiberties(Position liberty)
	{
		// TODO if in group set a liberty to group
		liberties.add(liberty);
	}

	@Override
	public void generateLiberties()
	{
		for (BoardPiece status : neighbours)
		{
			if (status instanceof Tile && ((Tile) status).getStatus() == TileStatus.EMPTY)
			{
				setALiberties(status.getPosition());
			}
		}
	}

	@Override
	public boolean isDead()
	{
		if (isInGroup())
			return currentGroup.isDead();
		return liberties.isEmpty();
	}

	private void findGroup(Stone stone)
	{
		if (stone != null)
		{

			if (stone != null && stone.getColor() == getColor())
			{
				if (isInGroup() && stone.isInGroup())
					getGroup().addGroup(stone.getGroup());
				else if (isInGroup())
					getGroup().addMember(stone);
				else if (stone.isInGroup())
					stone.getGroup().addMember(this);
				else
					new StoneGroup(this, stone);

			}
		}
	}

	public void updateNeighbours()
	{
		for (BoardPiece status : neighbours)
		{
			Stone temp = status instanceof Stone ? (Stone) status : null;
			if (temp != null && temp.getColor() == getColor())
			{
				temp.addNeighbours(this);
				findGroup(temp);

			} else if (temp != null)
			{
				temp.addNeighbours(this);
			}
		}
	}

	public StoneGroup getGroup()
	{
		return currentGroup;
	}

	public void setGroup(StoneGroup currentGroup)
	{
		if (currentGroup != null)
			this.currentGroup = currentGroup;
	}

	public boolean isInGroup()
	{
		return currentGroup != null;
	}

	@Override
	public void removeLiberties(Position liberty)
	{
		if (isInGroup())
			getGroup().removeLiberties(liberty);

		liberties.remove(liberty);
	}

	public void checkDead()
	{
		if (isInGroup())
			getGroup().checkDead();
		else if (isDead())
			dies();
	}

	public void dies()
	{
		for (BoardPiece status : neighbours)
		{
			Stone temp = status instanceof Stone ? (Stone) status : null;
			if (temp != null && temp.getColor() != getColor())
			{
				temp.setALiberties(getPosition());
			}
		}
	}

	public String toString()
	{
		String color = getColor() == Color.black ? "Black" : "White";
		String isGroup = isInGroup() ? " is in a group" : " isn't in a group";
		return color + " stone at:" + getXPosition() + " ," + getYPosition() + isGroup;
	}

	@Override
	public boolean equals(BoardPiece piece)
	{
		return piece instanceof Stone && this.getPosition().equals(piece.getPosition())
				&& this.getColor() == ((Stone) piece).getColor();
	}

}
