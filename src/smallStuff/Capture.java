package smallStuff;

public class Capture
{
	/**
	 * Integer that represent the amount of capture taken
	 */
	int capture;

	public Capture()
	{
		setCapture(0);
	}
	
	public Capture(int capture)
	{
		setCapture(capture);
	}

	public int getCapture()
	{
		return capture;
	}

	public void setCapture(int capture)
	{
		this.capture = capture;
	}

	public void addCapture()
	{
		capture++;
	}
	
}
