package instructions;

import vm.Frame;
import vm.NativeMethod;
import vm.VM;
import vm.VmException;

public class ApplyNative extends Instruction
{
	public ApplyNative()
	{
	}

	@Override
	public Instruction run(VM vm) throws VmException 
	{
		Object o = null;
		try
		{
			final Frame f1 = vm.currentFrame();
			o = f1.operandStack.pop();
			final NativeMethod m = (NativeMethod) o;
			final Object[] args = new Object[m.numArgs];
			for (int i = m.numArgs - 1; i >= 0; i--)
			{
				args[i] = f1.operandStack.pop();
			}
			final Object ret = m.run(args);
			if (ret != null)
				f1.operandStack.push(ret);
			return next;
		}
		catch (ClassCastException e)
		{
			throw new InternalVmError(String.format(
					"Top of stack not a native method in applyNative, instead is '%s'",
					o.toString()));
		}
	}
}
