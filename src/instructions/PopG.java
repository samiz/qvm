package instructions;

import vm.VM;
import vm.VmException;

public class PopG extends Instruction
{

	private final String sym;
	public PopG(String sym)
	{
		this.sym = sym;
	}
	@Override
	public Instruction run(VM vm) throws VmException
	{
		vm.globalEnvironment.put(sym,
				vm.currentFrame().operandStack.pop());
		return next;
	}

}
