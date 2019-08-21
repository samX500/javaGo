package fileManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import application.Game;
import memory.Memory;
import smallStuff.Color;
import smallStuff.Position;

public class ReadFile
{

	public static Memory openGame(Game game, String fileName)
	{
		CharList alphabet = new CharList(CharList.ALPHABET, 1);

		Memory memory = null;
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;

			while ((line = reader.readLine()) != null)
			{
				if (line.contains("SZ"))
				{
					int size = Character.getNumericValue(line.charAt(3));
					memory=new Memory(size, size, game.getPlayers());

				}
				if(line.contains("KM"))
				{
					int komi = Character.getNumericValue(line.charAt(3));
					memory.setKomi(komi);
				}
				if (line.charAt(0) == ';')
				{
					if (line.length() != 4)
					{
						int xPos = alphabet.getValue(line.charAt(3));
						int yPos = alphabet.getValue(line.charAt(4));
						memory.saveMove(new Position(xPos, yPos), line.charAt(1) == 'B' ? Color.black : Color.white);
					}
					else
						memory.saveMove(null, null);
				}
				
			

			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memory;
	}
}
