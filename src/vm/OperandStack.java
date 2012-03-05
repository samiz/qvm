package vm;

import java.util.ArrayList;
import java.util.List;

public class OperandStack<T>
{
	final List<T> _data = new ArrayList<>();
	public final T peek()
	{
		return _data.get(_data.size()-1);
	}
	public final void push(T object)
	{
		_data.add(object);
	}
	public final T pop()
	{
		return _data.remove(_data.size()-1);
	}
	public final T peekAt(int i)
	{
		int n = _data.size()-1;
		return _data.get(n - i);
	}
	
}
