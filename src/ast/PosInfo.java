package ast;

public class PosInfo
{
	public PosInfo(String fileName2, int line2, int col2, int pos2)
	{
		super();
		Line = line2;
		Col = col2;
		Pos = pos2;
		FileName = fileName2;
	}
	public int Line, Col, Pos;
	public String FileName;
	@Override public String toString()
	{
		return String.format("%s:%s,%s - %s", FileName, Line, Col, Pos);
	}
}
