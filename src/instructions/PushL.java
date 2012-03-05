package instructions;

import vm.VM;

public class PushL extends Instruction
{

	private final String sym;
	public PushL(String sym)
	{
		this.sym = sym;
	}
	@Override
	public Instruction run(VM vm)
	{
		vm.currentFrame().operandStack.push(vm.currentFrame().environment.get(sym));
		return next;
	}
}
