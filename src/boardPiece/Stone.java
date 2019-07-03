package boardPiece;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import boardPiece.Tile.TileStatus;
import exception.ConstructorException;
import exception.SuicideException;
import interfacePack.Killable;
import smallStuff.Position;

public class Stone extends BoardPiece implements Killable
{
	public enum Color
	{
		BLACK, WHITE
	};

	private StoneGroup currentGroup;
	private Color color;
	List<Position> liberties;
	private BoardPiece[] neighbours;

	public Stone(Color color, int x, int y, Board board) throws ConstructorException, SuicideException
	{
		super(x, y, board);
		liberties = new ArrayList<>();
		setColor(color);
		generateNeighbours();
		generateLiberties();
		updateNeighbours();
		if (isDead())
		{
			dies();
			throw new SuicideException("Stone dies at the same times it's played");
		}
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	private void generateNeighbours()
	{
		neighbours = new BoardPiece[4];
		neighbours[0] = getBoard().getBoardPiece(getXPosition() + 1, getYPosition());
		neighbours[1] = getBoard().getBoardPiece(getXPosition() - 1, getYPosition());
		neighbours[2] = getBoard().getBoardPiece(getXPosition(), getYPosition() + 1);
		neighbours[3] = getBoard().getBoardPiece(getXPosition(), getYPosition() - 1);
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
		if (validatePosition(liberty.getX(), liberty.getY(), getBoard()))
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

	private void findGroup(Stone stone) throws ConstructorException
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

	public void updateNeighbours() throws ConstructorException
	{
		for (BoardPiece status : neighbours)
		{
			Stone temp = status instanceof Stone ? (Stone) status : null;
			if (temp != null && temp.getColor() == getColor())
			{
				findGroup(temp);
				temp.addNeighbours(this);

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
		else
			liberties.remove(liberty);

		checkDead();
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

		//TODO find a way to make the stone die without getBoard()
		getBoard().setTile(TileStatus.EMPTY, getXPosition(), getYPosition());

	}
	

	public String toString()
	{
		String color = getColor() == Color.BLACK ? "Black" : "White";
		String isGroup = isInGroup() ? " is in a group" : " isn't in a group";
		return color + " stone at:" + getXPosition() + " ," + getYPosition() + isGroup;
	}

	@Override
	public BoardPiece clonePiece(BoardPiece piece)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
