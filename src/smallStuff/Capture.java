package smallStuff;

public class Capture
{
	/**
	 * Integer that represent the amount of capture taken
	 */
	int CaptureNum;

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
		return CaptureNum;
	}

	public void setCapture(int capture)
	{
		CaptureNum = capture;
	}
	
}
