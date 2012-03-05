package parsing;

import parsing.charpredicates.IsChar;

public class Scanner
{
	String input;
	public int Line;
	public int Col;
	public int Pos;
	public String FileName;

	public Scanner(String fileName, String input)
	{
		this.FileName = fileName;
		this.input = input;
		Pos = Line = Col = 0;
	}

	public boolean EOF()
	{
		return Pos >= input.length();
	}

	public char curChar()
	{
		return input.charAt(Pos);
	}

	public void nextChar()
	{
		Col++;
		if (curChar() == '\n')
		{
			Line++;
			Col = 0;
		}
		Pos++;
	}

	public boolean peek(IsChar test)
	{
		if(EOF()) 
			return false;
		if(test.run(curChar()))
			return true;
		return false;
	}

	public char readChar()
	{
		char tmp = curChar();
		nextChar();
		return tmp;
	}

	public boolean peek(String string)
	{
		if(Pos + string.length() > input.length())
			return false;
		if(input.substring(Pos, Pos + string.length()).equals(string))
			return true;
		return false;
	}

}