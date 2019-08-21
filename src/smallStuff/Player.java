package smallStuff;

public class Player
{
	/**
	 * Represent the capture of the player
	 */
	private Capture capture;

	/**
	 * Komi of the player
	 */
	private Komi komi;

	/**
	 * Creates a new player and set it's capture to 0
	 */
	public Player()
	{
		capture = new Capture(0);
		komi = new Komi(0.0);
	}

	public Player(Double komi)
	{
		capture = new Capture(0);
		this.komi = new Komi(komi);
	}
	
	public Player(int capture, Double komi)
	{
		this.capture = new Capture(capture);
		this.komi = new Komi(komi);
	}

	public Double getKomi()
	{
		return komi.getKomi();
	}

	public void setKomi(Double komi)
	{
		this.komi.setKomi(komi);
	}

	public int getCapture()
	{
		return capture.getCapture();
	}
	
	public void setCapture(int capture)
	{
		this.capture.setCapture(capture);
	}
	
	public void addCapture()
	{
		this.capture.addCapture();
	}
	
	public void removeCapture()
	{
		capture.setCapture(getCapture()-1);
	}

	public Player clonePlayer()
	{
		return new Player(this.getCapture(),this.getKomi());
	}
	
	public String toString()
	{
		return "player has " + capture + " captures";
	}

	
}
