package vm.nativemethods;

import vm.NativeMethod;

public class DivIntInt extends NativeMethod
{

	public DivIntInt()
	{
		super(2);
	}

	@Override
	public Object run(Object[] args)
	{
		final Integer i1 = (Integer) args[0];
		final Integer i2 = (Integer) args[1];
		return i1.intValue() / i2.intValue();
	}

}
