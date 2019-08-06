package boardPiece;

import java.util.ArrayList;
import java.util.List;

import exception.ConstructorException;
import interfacePack.Killable;
import smallStuff.Position;

public class StoneGroup implements Killable
{
	List<Stone> member;
	List<Position> liberties;

	public StoneGroup(Stone firstStone, Stone secondStone)
	{
		member = new ArrayList<>();
		liberties = new ArrayList<>();
		addMember(firstStone);
		addMember(secondStone);
	}

	@Override
	public List<Position> getLiberties()
	{
		return liberties;
	}

	@Override
	public void setALiberties(Position liberty)
	{
		liberties.add(liberty);
	}

	public void setLiberties(List<Position> liberty)
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
	public void removeLiberties(Position liberty)
	{
		liberties.remove(liberty);
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
