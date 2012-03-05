package instructions;

import vm.VM;

public class PopG extends Instruction
{

	private final String sym;
	public PopG(String sym)
	{
		this.sym = sym;
	}
	@Override
	public Instruction run(VM vm)
	{
		vm.globalEnvironment.put(sym,
				vm.currentFrame().operandStack.pop());
		return next;
	}

}
