package instructions;

import vm.VM;
import vm.VmException;

public class Import extends Instruction
{
	private final String className;
	public Import(String className)
	{
		this.className = className;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Instruction run(VM vm) throws VmException
	{
		Class theClass;
		try
		{
			theClass = Class.forName(className);
			vm.registerJavaClass(theClass);
		}
		catch (ClassNotFoundException e)
		{
			throw new VmException("Cannot load class: "+ className +"/" + e.getMessage());
		}
		return next;
	}


}
