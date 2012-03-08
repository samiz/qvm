package vm;

import instructions.Instruction;
import instructions.InternalVmError;

public abstract class NativeMethod implements IMethod
{
	public final int numArgs;

	public NativeMethod(int numArgs)
	{
		this.numArgs = numArgs;
	}

	public abstract Object run(Object[] args);

	@Override
	public Instruction apply(VM vm, Frame f1, boolean tail, Instruction next)
			throws InternalVmError
	{
		final Object[] args = new Object[this.numArgs];
		for (int i = this.numArgs - 1; i >= 0; i--)
		{
			args[i] = f1.operandStack.pop();
		}
		final Object ret = this.run(args);
		if (ret != null)
			f1.operandStack.push(ret);
		return next;

	}
}
