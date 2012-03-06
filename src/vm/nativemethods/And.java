package vm.nativemethods;

import vm.NativeMethod;

public class And extends NativeMethod
{

	public And()
	{
		super(2);
	}

	@Override
	public Object run(Object[] args)
	{
		final Boolean o1 = (Boolean) args[0];
		final Boolean o2 = (Boolean) args[1];
		return o1 && o2;
	}

}
