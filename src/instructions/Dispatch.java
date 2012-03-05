package instructions;

import vm.Frame;
import vm.VM;
import vm.VmException;

public class Dispatch extends Instruction
{
	private final String sym;
	private final int narg;

	public Dispatch(String sym, int narg)
	{
		this.sym = sym;
		this.narg = narg;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Instruction run(VM vm) throws VmException
	{
		final Frame f = vm.currentFrame();
		final Class[] types = new Class[narg];
		int c = 0;
		for (int i = types.length-1; i >= 0; i--)
		{
			types[i] = f.operandStack.peekAt(c++).getClass();
		}
		Object method = vm.dispatchn(sym, types);
		f.operandStack.push(method);
		return next;
	}
}
