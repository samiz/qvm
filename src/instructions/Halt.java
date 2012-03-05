package instructions;

import vm.VM;

public class Halt extends Instruction
{
	@Override
	public Instruction run(VM vm)
	{
		vm.halted = true;
		return null;
	}

}
