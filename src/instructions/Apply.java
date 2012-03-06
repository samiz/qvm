package instructions;

import vm.Frame;
import vm.Method;
import vm.VM;

public class Apply extends Instruction
{
	private final boolean tail;
	public Apply(boolean tail)
	{
		this.tail = tail;
	}
	@Override
	public Instruction run(VM vm)
	{
		final Frame f1 = vm.currentFrame();
		final Method m = (Method) f1.operandStack.pop();
		final Frame f2 = new Frame(m);
		
		for(int i=0; i<m.numArgs; i++)
		{
			f2.operandStack.push(f1.operandStack.pop());
		}
		if(tail)
			vm.currentProcess.callStack().pop();
		vm.currentProcess.callStack().push(f2);
		vm._currentFrame = f2;
		return next;
	}
}
