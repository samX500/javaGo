package memory;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import board.Board;
import exception.ConstructorException;

public class Memory
{
	private Path currentPath;

	public Memory() throws ConstructorException
	{
		// TODO check if null for first path can fuck shit up
		currentPath = new Path(0, null);
	}

	public Board getBoardAt(int boardAt)
	{
		return currentPath.getBoard(boardAt);
	}
	
	public void saveBoard(Board board)
	{
		currentPath.saveBoard(board);
	}

	public void addPath(int creationTurn) throws ConstructorException
	{
		currentPath.newPath(creationTurn);
	}
	
	public Path getSourcePath()
	{
		// TODO Check if doing this change the address for currentPath
		Path source = currentPath;
		while (source.getPreviousPath() != null)
			source = source.getPreviousPath();

		return source;
	}

	public void changePath(int[] direction)
	{
		// TODO test this
		//TODO direction must contain number so that this doesn't throw arrayOutOfBound
		currentPath = getSourcePath();

		for (int i = 0; i < direction.length; i++)
			currentPath = currentPath.getNode().get(direction[1]);
	}

}
