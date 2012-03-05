package parsing;

import ast.AST;

@SuppressWarnings("serial")
public class MatchError extends RuntimeException
{

	public AST ast;
	public Object matchee;

	public MatchError(AST ast, Object matchee)
	{
		super(ast.posInfo().toString()+"| " + "Cannot match '"+ast.toString()+"'"+ " and '"+matchee.toString()+"'");
		this.ast = ast;
		this.matchee = matchee;
	}

}
