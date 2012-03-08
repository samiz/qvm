package instructions;

import vm.VM;
import vm.VmException;

public class PopL extends Instruction
{

	private final String sym;
	public PopL(String sym)
	{
		this.sym = sym;
	}
	@Override
	public Instruction run(VM vm) throws VmException
	{
		vm.currentFrame().environment.put(sym,
				vm.currentFrame().operandStack.pop());
		return next;
	}

}
