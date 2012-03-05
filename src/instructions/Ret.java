package instructions;

import vm.VM;
import vm.VmException;

public class Ret extends Instruction
{

	@Override
	public Instruction run(VM vm) throws VmException
	{
		Object retVal = vm.currentFrame().operandStack.pop();
		vm.currentProcess.callStack().pop();
		vm.currentFrame().operandStack.push(retVal);
		return next;
	}

}
