package vm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import instructions.*;

public class Method
{
	public final List<Instruction> code = new ArrayList<>();
	public final Map<String, Instruction> labels = new HashMap<>();
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
}
