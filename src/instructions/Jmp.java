package instructions;

import vm.VM;
import vm.VmException;

public class Jmp extends Instruction
{
	private final String sym;
	public Jmp(String sym)
	{
		this.sym = sym;
	}
	@Override
	public Instruction run(VM vm) throws VmException
	{
		return vm.currentFrame().method.labels.get(sym);
	}

}
