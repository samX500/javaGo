package exception;

public class ConstructorException extends Exception
{
	public ConstructorException()
	{
		super();
	}
	
	public ConstructorException(String message)
	{
		super(message);
	}
}
