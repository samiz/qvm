package vm.nativemethods;

import vm.NativeMethod;

public class AddDoubleDouble extends NativeMethod
{

	public AddDoubleDouble()
	{
		super(2);
	}

	@Override
	public Object run(Object[] args)
	{
		final Double i1 = (Double) args[0];
		final Double i2 = (Double) args[1];
		return i1.doubleValue() + i2.doubleValue();
	}

}
