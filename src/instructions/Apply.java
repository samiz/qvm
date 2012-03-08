package instructions;

import vm.Frame;
import vm.IMethod;
import vm.VM;
import vm.VmException;

public class Apply extends Instruction
{
	private final boolean tail;
	public Apply(boolean tail)
	{
		this.tail = tail;
	}
	@Override
	public Instruction run(VM vm) throws VmException
	{
		final Frame f1 = vm.currentFrame();
		final IMethod m = (IMethod) f1.operandStack.pop();
		return m.apply(vm, f1, tail, next);
	}
}
