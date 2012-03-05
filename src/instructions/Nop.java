package instructions;

import vm.VM;
import vm.VmException;

public class Nop extends Instruction
{

	@Override
	public Instruction run(VM vm) throws VmException
	{
		return next;
	}

}
