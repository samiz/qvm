package vm.nativemethods;

import vm.NativeMethod;

public class NotEqObjectObject extends NativeMethod
{

	public NotEqObjectObject()
	{
		super(2);
	}

	@Override
	public Object run(Object[] args)
	{
		final Object o1 = args[0];
		final Object o2 = args[1];
		return !o1.equals(o2);
	}

}
