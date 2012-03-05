package vm;

import java.util.Stack;

public class QProcess
{
	final Stack<Frame> _callStack = new Stack<Frame>();
	public final Stack<Frame> callStack()
	{
		return _callStack;
	}

}
