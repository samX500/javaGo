package interfacePack;

import java.util.ArrayList;
import java.util.List;

import smallStuff.Position;

public interface Killable
{
	 
	 List<Position> getLiberties();
	 
	 void setALiberties(Position liberty);
	 
	 void generateLiberties();
	 
	 void removeLiberties(Position liberty);
	 
	 void checkDead();
	 
	 void dies();

	 boolean isDead();
}
