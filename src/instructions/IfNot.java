package instructions;

import vm.Frame;
import vm.VM;
import vm.VmException;

public class IfNot extends Instruction
{
	private Object sym;
	public IfNot(String sym)
	{
		this.sym = sym;
	}
	@Override
	public Instruction run(VM vm) throws VmException
	{
		final Frame f = vm.currentFrame();
		Boolean b = (Boolean) f.operandStack.pop();
		if(b)
			return next;
		
		return f.method.labels.get(sym);
	}

}
