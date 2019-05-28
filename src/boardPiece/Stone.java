package boardPiece;

import java.util.List;

import board.Board;
import exception.ConstructorException;
import exception.SuicideException;
import interfacePack.Killable;

public class Stone extends BoardPiece implements Killable
{
	private StoneGroup currentGroup;
	private boolean isBlack;

	private BoardPiece[] neighbours;

	Stone(boolean color)
			throws ConstructorException, SuicideException
	{
		super();
		setColor(color);
		generateNeighbours();
		generateLiberties();
		updateNeighbours();
		if (isDead())
		{
			dies();
			throw new SuicideException(
					"Stone dies at the same times it's played");
		}
	}

	public boolean getColor()
	{
		return isBlack;
	}

	public void setColor(boolean color)
	{
		isBlack = color;
	}

	private void generateNeighbours()
	{
		// TODO this looks awful
		neighbours[0] = getBoard()
				.getBoardPiece(getXPosition() + 1, getYPosition());
		neighbours[1] = getBoard()
				.getBoardPiece(getXPosition() - 1, getYPosition());
		neighbours[2] = getBoard()
				.getBoardPiece(getXPosition(), getYPosition() + 1);
		neighbours[3] = getBoard()
				.getBoardPiece(getXPosition(), getYPosition() - 1);
	}

	@Override
	public List<int[]> getLiberties()
	{
		return liberties;
	}

	@Override
	public void setALiberties(int[] liberty)
	{
		if (validatePosition(liberty,getBoard()))
			liberties.add(liberty);
	}

	@Override
	public void generateLiberties()
	{
		for (BoardPiece status : neighbours)
		{
			if (status.getStatus() == TileStatus.EMPTY)
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
			Stone temp = status.getContent();
			if (temp != null && temp.getColor() == getColor())
				findGroup(temp);
			else if (temp != null)
				temp.removeLiberties(getPosition());

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
			Stone temp = status.getContent();

			if (temp != null && temp.getColor() != getColor())
				temp.setALiberties(getPosition());

			try
			{
				super.setContent(TileStatus.EMPTY);
			}
			catch (ConstructorException e)
			{
				e.printStackTrace();
			}
			catch (SuicideException e)
			{
				e.printStackTrace();
			}
		}
	}

}
