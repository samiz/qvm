package vm.nativemethods;

import vm.NativeMethod;

public class EqObjectObject extends NativeMethod
{

	public EqObjectObject()
	{
		super(2);
	}

	@Override
	public Object run(Object[] args)
	{
		final Object o1 = args[0];
		final Object o2 = args[1];
		return o1.equals(o2);
	}

}
