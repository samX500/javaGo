package smallStuff;

public class Score
{
	/**
	 * Integer that represent a score
	 */
	private int score;
	
	public Score(int score)
	{
		setScore(score);
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}
	
	public void addScore(int score)
	{
		this.score += score;
	}
	
	public void removeScore(int score)
	{
		addScore(-score);
	}
	
	public String toString()
	{
		//TODO figure out how to add 0.5 for player 2
		return ""+score;
	}
	
}
