package smallStuff;

public class Player
{
	/**
	 * Represent the score of the player
	 */
	private Score score;

	// TODO maybe add a name if needed

	/**
	 * Creates a new player and set it's score to 0
	 */
	public Player()
	{
		score = new Score(0);
	}

	public Player(int score)
	{
		this.score = new Score(score);
	}

	public Score getScore()
	{
		return score;
	}

	public String toString()
	{
		return "player has " + score + " points";
	}
}
