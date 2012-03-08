package vm;

import instructions.Instruction;
import instructions.InternalVmError;

public interface IMethod
{
	Instruction apply(VM vm, Frame f1, boolean tail, Instruction next) throws InternalVmError;
}
