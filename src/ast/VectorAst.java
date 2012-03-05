package ast;

import java.util.ArrayList;
import java.util.List;

import parsing.MatchError;

public class VectorAst extends ASTImpl
{
	public List<AST> value = new ArrayList<AST>();
	public VectorAst(PosInfo info)
	{
		super(info);
	}
	@Override
	public Object eval()
	{
		return value;
	}
	@Override public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(AST v : value)
		{
			sb.append(v.toString());
			sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
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
		if(i>=this.value.size())
			return false;
		Object at = this.value.get(i);
		if(!(at instanceof Symbol))
			return false;
		return ((Symbol)at).matchSym(value);
	}
	@Override
	public boolean matchAt(int i, int value)
	{
		if(i>=this.value.size())
			return false;
		Object at = this.value.get(i);
		if(!(at instanceof NumLiteral))
			return false;
		return ((NumLiteral)at).match(value);
	}
	
	@Override
	public String symbolAt(int i)
	{
		if(i>=this.value.size())
			throw new MatchError(this, i);
		Object at = this.value.get(i);
		if(!(at instanceof Symbol))
			throw new MatchError(this, i);
		return ((Symbol) at).toString();
	}
	@Override
	public int intAt(int i)
	{
		// TODO match out of bound errors need a different message
		if(i>=this.value.size())
			throw new MatchError(this, i);
		Object at = this.value.get(i);
		if(!(at instanceof NumLiteral))
			throw new MatchError(this, i);
		return ((NumLiteral) at).value;
	}
	@Override
	public VectorAst VectorAt(int i)
	{
		if(i>=this.value.size())
			throw new MatchError(this, i);
		Object at = this.value.get(i);
		if(!(at instanceof VectorAst))
			throw new MatchError(this, i);
		return ((VectorAst) at);
	}
	@Override
	public AST at(int i)
	{
		return value.get(i);
	}
	@Override
	public int countElems()
	{
		return value.size();
	}

}
