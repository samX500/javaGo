package boardPiece;

import java.util.ArrayList;
import java.util.List;

import exception.ConstructorException;
import interfacePack.Killable;

public class StoneGroup implements Killable
{
	List<Stone> member;
	List<int[]> liberties = new ArrayList<>();

	public StoneGroup(Stone firstStone, Stone secondStone) throws ConstructorException
	{
		if (firstStone != null && secondStone != null)
		{
			member = new ArrayList<>();
			addMember(firstStone);
			addMember(secondStone);
		} else
		{
			throw new ConstructorException("Issue trying to create a new stoneGroup");
		}
	}

	@Override
	public List<int[]> getLiberties()
	{
		return liberties;
	}

	@Override
	public void setALiberties(int[] liberty)
	{
		liberties.add(liberty);
	}

	public void setLiberties(List<int[]> liberty)
	{
		if (liberty != null)
			liberties.addAll(liberty);
	}

	@Override
	public void generateLiberties()
	{
		for (Stone stone : member)
			setLiberties(stone.getLiberties());

	}

	@Override
	public boolean isDead()
	{
		return liberties.isEmpty();
	}

	public void checkDead()
	{
		if (isDead())
			dies();
	}

	public void dies()
	{
		for (Stone stone : member)
			stone.dies();
		endGroup();
	}

	@Override
	public void removeLiberties(int[] liberty)
	{
		liberties.remove(liberty);
		checkDead();
	}

	public void addMember(Stone stone)
	{
		if (stone != null)
		{
			member.add(stone);
			setLiberties(stone.getLiberties());
			stone.setGroup(this);
		}
	}

	private void endGroup()
	{
		member.clear();
	}

	public void addGroup(StoneGroup group)
	{
		if (group != null && this != group)
		{
			for (Stone stone : group.getMember())
				addMember(stone);
			group.endGroup();
		}
	}

	public List<Stone> getMember()
	{
		return member;
	}

	public String toString()
	{
		String toString = "";
		for (Stone stone : member)
		{
			toString += stone.toString() + "\n";
		}
		return toString;
	}
}
