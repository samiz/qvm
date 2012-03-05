package instructions;

import vm.VM;

public class PushV extends Instruction
{

	private final Object val;
	public PushV(Object val)
	{
		this.val= val;
	}
	@Override
	public Instruction run(VM vm)
	{
		vm.currentFrame().operandStack.push(val);
		return next;
	}

}
