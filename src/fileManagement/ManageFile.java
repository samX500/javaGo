package fileManagement;

import java.io.*;
import java.util.Stack;

import application.Game;
import board.Board;
import memory.Memory;
import memory.Move;
import smallStuff.Color;
import smallStuff.Player;
import smallStuff.Position;

public class ManageFile
{

	/**
	 * Read the file named fileName if it exist and is a sgs file and return a
	 * memory to re-create the game in the file
	 * 
	 * @param fileName Name of the file
	 * @return The memory of the game in the file or null if the file is not
	 *         valid
	 */
	public static Memory openGame(String fileName)
	{
		Memory memory = null;
		Player[] players = new Player[]
		{ new Player(), new Player() };
		if (getExtension(fileName).equals("sgf"))
		{
			CharList alphabet = new CharList(CharList.ALPHABET, 1);

			try
			{
				BufferedReader reader = new BufferedReader(
						new FileReader(fileName));

				String line;

				while ((line = reader.readLine()) != null)
				{
					if (line.contains("SZ"))
					{
						String getData = getInsideBraces(line);
						if (stringIsInt(getData))
						{
							int size = Integer.parseInt(getData) + Board.BORDER;
							memory = new Memory(size, size, players);
						}

					}
					if (line.contains("KM"))
					{
						String getData = getInsideBraces(line);

						if (stringIsDouble(getData))
						{
							int komi = (int) Double.parseDouble(getData);
							memory.setKomi(komi);
						}

					}
					if (line.charAt(0) == ';')
					{
						String data = getInsideBraces(line);
						if (data.isEmpty())
							memory.saveMove(null, null);
						else
						{
							int xPos = alphabet.getValue(data.charAt(0));
							int yPos = alphabet.getValue(data.charAt(1));

							memory.saveMove(new Position(xPos, yPos),
									line.charAt(1) == 'B' ? Color.black
											: Color.white);
						}

					}

				}

				reader.close();
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return memory;
	}

	public static void WriteGame(Memory memory, String fileName)
	{
		CharList alphabet = new CharList(CharList.ALPHABET, 1);
		BufferedWriter writer = null;
		File file = new File("savedGame\\"+fileName+".sgf");
		try
		{
			writer = new BufferedWriter(new FileWriter(file));

			Stack<Move> moveStack = memory.getMove();

			memory.getDimension().removeDim(Board.BORDER);
			String size = "SZ"+putInBraces(memory.getDimension().squareDim());
			String komi = "KM"+putInBraces(""+memory.getKomi());
			
			writer.write(size);
			writer.newLine();
			writer.write(komi);
			writer.newLine();
			
			while (!moveStack.isEmpty())
			{
				Move move = moveStack.pop();
				String coordinate = alphabet.getChar(move.getX()) + ""
						+ alphabet.getChar(move.getY());
				String toWrite = ";" + putInBraces(coordinate);

				writer.write(toWrite);
				writer.newLine();

			}

			writer.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Receives a string and return the content inside the braces of the string,
	 * or an empty string if the string has no braces
	 * 
	 * @param string String to be decrypted
	 * @return subString inside of the braces of the string passed in parameter
	 */
	public static String getInsideBraces(String string)
	{
		String inBraces = "";
		boolean getBraces = false;
		for (int i = 0; i < string.length(); i++)
		{
			if (getBraces && string.charAt(i) != ']')
				inBraces += string.charAt(i);
			if (string.charAt(i) == '[')
				getBraces = true;

		}

		return inBraces;
	}

	public static String putInBraces(String string)
	{
		return "[" + string + "]";
	}

	/**
	 * Receives a string and return true if it only contains integer
	 * 
	 * @param string String to test
	 * @return True if the string received only contains integer.
	 */
	private static boolean stringIsInt(String string)
	{
		try
		{
			int test = Integer.parseInt(string);
		}
		catch (NumberFormatException e)
		{
			return false;
		}

		return true;
	}

	/**
	 * Receives a string and return true if it only contains a double
	 * 
	 * @param string String to test
	 * @return True if the string received only contains a double.
	 */
	private static boolean stringIsDouble(String string)
	{
		try
		{
			double test = Double.parseDouble(string);
		}
		catch (NumberFormatException e)
		{
			return false;
		}

		return true;
	}

	/**
	 * Receive the name of a file and returns the extension of the file or an
	 * empty string if that received string didn't have an extension
	 * 
	 * @param file Name of the file
	 * @return The extension of the file or an empty string if the file has no
	 *         extension
	 */
	private static String getExtension(String file)
	{
		String extension = "";
		boolean foundDots = false;

		for (int i = 0; i < file.length(); i++)
		{
			char charAt = file.charAt(i);

			if (charAt == '.')
			{
				if (foundDots)
					extension = "";
				else
					foundDots = true;
			}
			else if (foundDots)
				extension += charAt;
		}
		return extension;
	}

}
