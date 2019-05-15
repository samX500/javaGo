package boardPiece;

import java.util.List;

import Board.Board;
import Exception.ConstructorException;
import interfacePack.Killable;

public class Stone extends BoardPiece implements Killable
{
	private StoneGroup currentGroup;
	private boolean isBlack;

	private BoardPiece[] neighbours;

	Stone(int[] position, boolean color) throws ConstructorException
	{
		if (validatePosition(position))
			setPosition(position);
		setColor(color);
		generateNeighbours();
		generateLiberties();
		findGroup();
	}

	public boolean getColor()
	{
		return isBlack;
	}

	public void setColor(boolean color)
	{
		isBlack = color;
	}

	private boolean validatePosition(int[] position)
	{
		return position[0] > 0 && position[1] > 0
				&& position[0] < getBoard().getLenght()
				&& position[1] < getBoard().getWidth();
	}

	private void generateNeighbours()
	{
		// TODO this looks awful
		neighbours[0] = getBoard()
				.getGoButton(getXPosition() + 1, getYPosition()).getPiece();
		neighbours[1] = getBoard()
				.getGoButton(getXPosition() - 1, getYPosition()).getPiece();
		neighbours[2] = getBoard()
				.getGoButton(getXPosition(), getYPosition() + 1).getPiece();
		neighbours[3] = getBoard()
				.getGoButton(getXPosition(), getYPosition() - 1).getPiece();
	}

	@Override
	public List<int[]> getLiberties()
	{
		return liberties;
	}

	@Override
	public void setALiberties(int[] liberty)
	{
		if (validatePosition(liberty))
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

	private void findGroup() throws ConstructorException
	{
		for (BoardPiece status : neighbours)
		{
			Stone temp = status.getContent() instanceof Stone
					? ((Stone) status.getContent())
					: null;

			if (temp != null && temp.getColor() == getColor())
			{
				if (isInGroup() && temp.isInGroup())
					getGroup().addGroup(temp.getGroup());
				else if (isInGroup())
					getGroup().addMember(temp);
				else if (temp.isInGroup())
					temp.getGroup().addMember(this);
				else
					new StoneGroup(this, temp);

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
		liberties.remove(liberty);

	}

}
