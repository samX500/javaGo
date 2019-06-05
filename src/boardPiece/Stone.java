package boardPiece;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;
import interfacePack.Killable;

public class Stone extends BoardPiece implements Killable
{
	private StoneGroup currentGroup;
	private Color color;

	List<int[]> liberties;
	private BoardPiece[] neighbours;

	public Stone(Color color, int[] position, Board board) throws ConstructorException, SuicideException
	{
		super(position, board);
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
		int index = validateNeighbours(newNeighbours.getPosition());
		if (index >= 0)
		{
			neighbours[index] = newNeighbours;
			removeLiberties(newNeighbours.getPosition());
		}
	}

	private int validateNeighbours(int[] position)
	{
		if (position[0] == getXPosition() + 1 && position[1] == getYPosition())
			return 0;
		else if (position[0] == getXPosition() - 1 && position[1] == getYPosition())
			return 1;
		else if (position[0] == getXPosition() && position[1] == getYPosition() + 1)
			return 2;
		else if (position[0] == getXPosition() && position[1] == getYPosition() - 1)
			return 3;
		return -1;
	}

	@Override
	public List<int[]> getLiberties()
	{
		return liberties;
	}

	@Override
	public void setALiberties(int[] liberty)
	{
		if (validatePosition(liberty, getBoard()))
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
	public void removeLiberties(int[] liberty)
	{
		if (isInGroup())
			getGroup().removeLiberties(liberty);

		int index = findLiberty(liberty);
		if (index >= 0)
			liberties.remove(index);
		checkDead();
	}

	private int findLiberty(int[] liberty)
	{
		int index = -1;

		for (int i = 0; i < liberties.size() && index == -1; i++)
		{
			int[] temp = liberties.get(i);
			if (temp[0] == liberty[0] && temp[1] == liberty[1])
			{
				index = i;
			}
		}
		return index;
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

		getBoard().setTile(TileStatus.EMPTY, getXPosition(), getYPosition());

	}

	public String toString()
	{
		String color = getColor() == Color.BLACK ? "Black" : "White";
		String isGroup = isInGroup() ? " is in a group" : " isn't in a group";
		return color + " stone at:" + getXPosition() + " ," + getYPosition() + isGroup;
	}
}
