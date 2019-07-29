package smallStuff;

public class Player
{
	/**
	 * Represent the capture of the player
	 */
	private Capture capture;

	// TODO maybe add a name if needed

	/**
	 * Creates a new player and set it's capture to 0
	 */
	public Player()
	{
		capture = new Capture(0);
	}

	public Player(int capture)
	{
		this.capture = new Capture(capture);
	}

	public Capture getCapture()
	{
		return capture;
	}

	public String toString()
	{
		return "player has " + capture + " captures";
	}
}
