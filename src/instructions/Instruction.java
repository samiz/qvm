package instructions;

import vm.VM;
import vm.VmException;

public abstract class Instruction
{
	public Instruction next;
	public Object metaData;
	public abstract Instruction run(VM vm) throws VmException;
	public Instruction meta(Object m)
	{
		metaData = m;
		return this;
	}
}
