package vm.nativemethods;

import vm.NativeMethod;

public class PrintObject extends NativeMethod
{

	public PrintObject()
	{
		super(1);
	}

	@Override
	public Object run(Object[] args)
	{
		System.out.print(args[0].toString());
		return null;
	}

}
