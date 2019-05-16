package interfacePack;

import java.util.ArrayList;
import java.util.List;

public interface Killable
{
	 List<int[]> liberties = new ArrayList<>();
	 
	 List<int[]> getLiberties();
	 
	 void setALiberties(int[] liberty);
	 
	 void generateLiberties();
	 
	 void removeLiberties(int[] liberty);
	 
	 void checkDead();
	 
	 void dies();

	 boolean isDead();
}
