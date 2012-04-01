package ast;

import parsing.MatchError;

public class StrLiteral extends ASTImpl 
{

	public String value;

	public StrLiteral(PosInfo info, String str)
	{
		super(info);
		this.value = str;
	}

	@Override
	public Object eval()
	{
		return value;
	}
	@Override public String toString()
	{
		return value;
	}

	@Override
	public boolean match(int value)
	{
		return false;
	}

	@Override
	public boolean matchSym(String value)
	{
		return false;
	}

	@Override
	public boolean matchSymAt(int i, String value)
	{
		return false;
	}

	@Override
	public boolean matchAt(int i, int value)
	{
		return false;
	}

	@Override
	public String symbolAt(int i)
	{
		throw new MatchError(this, i);
	}

	@Override
	public int intAt(int i)
	{
		throw new MatchError(this, i);
	}

	@Override
	public VectorAst VectorAt(int i)
	{
		throw new MatchError(this, i);
	}

	@Override
	public AST at(int i)
	{
		throw new MatchError(this, i);
	}
	@Override
	public int countElems()
	{
		throw new MatchError(this, "a vector");
	}
	@Override
	public VectorAst tail()
	{
		throw new MatchError(this, "a vector");
	}
	@Override
	public String stringAt(int i)
	{
		throw new MatchError(this, "a vector");
	}

}
