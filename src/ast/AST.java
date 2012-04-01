package ast;

public interface AST
{
	PosInfo posInfo();
	AST next();
	void setNext(AST n);
	Object eval();
	
	boolean match(int value);
	boolean matchSym(String value);
	boolean matchSymAt(int i, String value);
	boolean matchAt(int i, int value);
	//boolean matchAt(int i, String value);
	String symbolAt(int i);
	int intAt(int i);
	VectorAst VectorAt(int i);
	AST at(int i);
	int countElems();
	VectorAst tail();
	String stringAt(int i);
	
	
	
}