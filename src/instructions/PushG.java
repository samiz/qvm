package instructions;

import vm.VM;

public class PushG extends Instruction
{

	private final String sym;
	public PushG(String sym)
	{
		this.sym = sym;
	}
	@Override
	public Instruction run(VM vm)
	{
		vm.currentFrame().operandStack.push(vm.globalEnvironment.get(sym));
		return next;
	}

}
