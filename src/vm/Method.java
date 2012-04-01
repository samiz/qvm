package vm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import instructions.*;

public class Method implements IMethod
{
	public final List<Instruction> code = new ArrayList<Instruction>();
	public final Map<String, Instruction> labels = new HashMap<String, Instruction>();
	public final int numArgs;
	
	private Instruction lastInstruction = null;
	public Method(int numArgs)
	{
		this.numArgs = numArgs;
	}
	public void addInstruction(Instruction i)
	{
		code.add(i);
		
		if(lastInstruction !=null)
			lastInstruction.next = i;
		lastInstruction = i;
	}
	// add(a, b, c)
	// push a
	// push b
	// push c
	// dispatch add 3
	// apply
	@Override
	public Instruction apply(VM vm, Frame f1, boolean tail, Instruction next) throws InternalVmError
	{
		final Frame f2 = new Frame(this);
		
		for(int i=0; i<this.numArgs; i++)
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
