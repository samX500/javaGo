package smallStuff;

import java.util.Stack;

public class stackOpperation
{
	public static <T> Stack<T> reverseStack(Stack<T> stack)
	{
		Stack<T> newStack = new Stack();
		while(!stack.isEmpty())
			newStack.push(stack.pop());
		return newStack;
	}
	
	public static <T> Stack<T> cloneStack(Stack<T> stack)
	{
		Stack<T> clone = new Stack();
		clone.addAll(stack);
		return clone;
	}
	
}
