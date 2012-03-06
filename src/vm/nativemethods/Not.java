package vm.nativemethods;

import vm.NativeMethod;

public class Not extends NativeMethod
{

	public Not()
	{
		super(2);
	}

	@Override
	public Object run(Object[] args)
	{
		final Boolean o1 = (Boolean) args[0];
		return !o1;
	}

}
