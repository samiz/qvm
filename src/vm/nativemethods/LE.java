package vm.nativemethods;

import vm.NativeMethod;

public class LE extends NativeMethod
{

	public LE()
	{
		super(2);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object run(Object[] args)
	{
		final Comparable o1 = (Comparable) args[0];
		final Comparable o2 = (Comparable) args[1];
		return o1.compareTo(o2) <= 0;
	}

}
