package fileManagement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;


/**
 * HashMap that give value the characters
 */

public class CharList
{
	public static final char[] ALPHABET = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	private HashMap<Character, Integer> character;

	/**
	 * Instatiate an HashMap containing a key for each character charList and a
	 * value coresponding to their emplacement in the list
	 * 
	 * @param charList List of character
	 */
	public CharList(char[] charList)
	{
		character = new HashMap<>();
		generateMap(charList, 0);
	}

	/**
	 * Instatiate an HashMap containing a key for each character charList and a
	 * value coresponding to the starting value additioned with their emplacement in
	 * the list
	 * 
	 * @param charList      List of character
	 * @param startingValue Starting value for the value of the character
	 */
	public CharList(char[] charList, int startingValue)
	{
		character = new HashMap<>();
		generateMap(charList, startingValue);
	}

	private void generateMap(char[] charList, int startingValue)
	{
		for (int i = 0; i < charList.length; i++)
			character.put(charList[i], startingValue + i);
	}

	/**
	 * return the value of a character that is key of the hashMap
	 * 
	 * @param character key to access the HashMap
	 * @return value at the key character
	 * @throws NullPointerException if character isn't a key of the hashMap
	 */
	public int getValue(char character) throws NullPointerException
	{
		return this.character.get(character);
	}
	
	public String toString()
	{
		return character.entrySet().toString();
	}

}
