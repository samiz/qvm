package vm.nativemethods;

import vm.NativeMethod;

public class AddStringString extends NativeMethod
{

	public AddStringString()
	{
		super(2);
	}

	@Override
	public Object run(Object[] args)
	{
		final String s1 = (String) args[0];
		final String s2 = (String) args[1];
		return s1 + s2;
	}

}
