package fileManagement;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import smallStuff.*;

/**
 * HashMap that give value the characters
 */

public class CharList
{
	public static final char[] ALPHABET = new char[]
	{ 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	private List<CharInt> characterList;

	public CharList(char[] charList)
	{
		characterList = new LinkedList<>();
		generateMap(charList, 0);
	}

	public CharList(char[] charList, int startingValue)
	{
		characterList = new LinkedList<>();
		generateMap(charList, startingValue);
	}

	private void generateMap(char[] charList, int startingValue)
	{
		for (int i = 0; i < charList.length; i++)
			characterList.add(new CharInt(charList[i], startingValue + i));
	}

	/**
	 * Receive a character and return the corresponding integer 
	 * 
	 * @param character character received
	 * @return corresponding integer 
	 * @throws NullPointerException
	 */
	public int getValue(char character) throws NullPointerException
	{
		for (CharInt charInt : characterList)
			if (charInt.getCharacter() == character)
				return charInt.getInteger();

		throw new NullPointerException();
	}

	/**
	 * Receive an integer and return the corresponding character
	 * @param integer integer received
	 * @return corresponding character 
	 * @throws NullPointerException
	 */
	public char getChar(int integer) throws NullPointerException
	{
		for (CharInt charInt : characterList)
			if (charInt.getInteger() == integer)
				return charInt.getCharacter();

		throw new NullPointerException();
	}

	public String toString()
	{
		return "";
	}

}
