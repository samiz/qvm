package instructions;

import ast.AST;
import vm.VM;
import vm.VmException;

public class PushQuote extends Instruction
{
	final private AST expr;
	public PushQuote(AST expr)
	{
		this.expr = expr;
	}
	@Override
	public Instruction run(VM vm) throws VmException
	{
		vm.currentFrame().operandStack.push(expr);
		return next;
	}

}
