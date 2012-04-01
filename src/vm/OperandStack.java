package vm;

import instructions.InternalVmError;
import interpreter.Utils;

import java.util.ArrayList;
import java.util.List;

public class OperandStack<T>
{
	final List<T> _data = new ArrayList<T>();

	public final T peek() throws InternalVmError
	{
		if(Utils.debug && _data.isEmpty())
			throw new InternalVmError("Peeking atempty operand stack");
		return _data.get(_data.size()-1);
	}
	public final void push(T object)
	{
		_data.add(object);
	}
	public final T pop() throws InternalVmError
	{
		if(Utils.debug && _data.isEmpty())
			throw new InternalVmError("Popping from empty operand stack");
		return _data.remove(_data.size()-1);
	}
	public final T peekAt(int i) throws InternalVmError
	{
		int n = _data.size()-1;
		if(Utils.debug && ((n-i) <0 || _data.size() <= (n-i)))
			throw new InternalVmError("PeekAt from stack with non-sufficient elements");
		return _data.get(n - i);
	}
	
}
