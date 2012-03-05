package interpreter;

import ast.AST;

@SuppressWarnings("serial")
public class NonDecodableInstructionException extends InterpreterException
{

	public AST mnemonic;

	public NonDecodableInstructionException(AST i)
	{
		super("Non decodable instruction mnemonic: '"+ i.toString()+"'");
		this.mnemonic = i;
	}

}
