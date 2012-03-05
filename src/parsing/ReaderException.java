package parsing;

import ast.PosInfo;

@SuppressWarnings("serial")
public class ReaderException extends Exception
{
	public PosInfo posInfo;

	public ReaderException(PosInfo posInfo, String string)
	{
		super( posInfo.toString()+ "| " + string);
		this.posInfo = posInfo;
	}

}
