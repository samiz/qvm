package ast;

public abstract class ASTImpl implements AST
{
	PosInfo _posInfo;
	AST _next;
	public ASTImpl (PosInfo info)
	{
		this._posInfo = info;
	}
	@Override
	public PosInfo posInfo()
	{
		return _posInfo;
	}
	@Override
	public AST next()
	{
		return _next;
	}
	@Override
	public void setNext(AST n)
	{
		_next = n;
	}
	@Override
	public abstract Object eval();
}