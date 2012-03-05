package instructions;

import vm.VmException;

@SuppressWarnings("serial")
public class InternalVmError extends VmException
{

	public InternalVmError(String string)
	{
		super(string);
	}

}
