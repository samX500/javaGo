package boardPiece;

import java.util.ArrayList;
import java.util.List;

import exception.ConstructorException;
import interfacePack.Killable;

public class StoneGroup implements Killable
{
	List<Stone> member;
	List<int[]> liberties;

	public StoneGroup(Stone firstStone, Stone secondStone) throws ConstructorException
	{
		if (firstStone != null && secondStone != null)
		{
			member = new ArrayList<>();
			liberties = new ArrayList<>();
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
		int index = findLiberty(liberty);
		if(index >=0)
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
