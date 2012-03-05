package vm;

import instructions.Instruction;

import java.util.HashMap;
import java.util.Map;

public class Frame
{
	public final Map<String, Object> environment = new HashMap<>();
	public final Method method;
	public final OperandStack<Object> operandStack = new OperandStack<>();
	public Instruction currentInstruction;
	
	
	public Frame(Method method)
	{
		this.method = method;
		currentInstruction = method.code.get(0);
	}
}
