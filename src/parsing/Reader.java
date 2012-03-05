package parsing;

import parsing.charpredicates.IsChar;
import ast.AST;
import ast.NumLiteral;
import ast.PosInfo;
import ast.StrLiteral;
import ast.Symbol;
import ast.VectorAst;

public class Reader
{
	public static AST read(String program, String fileName) throws ReaderException
	{
		Scanner s = new Scanner(fileName, program);
		AST ret = read(s);
		skipSpacing(s);
		if(!s.EOF())
			throw new ReaderException(positionInfoFromScanner(s), "Garbage at end of program");
		return ret;
	}
	private static PosInfo positionInfoFromScanner(Scanner s)
	{
		return new PosInfo(s.FileName, s.Line, s.Col, s.Pos);
	}
	public static AST read(Scanner s) throws ReaderException
	{
		AST ret = null;
		PosInfo posInfo;
		
		if(s.EOF())
			return null;
		skipSpacing(s);
		
		if(s.peek(IsChar.digit))
		{
			posInfo = new PosInfo(s.FileName, s.Line, s.Col, s.Pos);
			String result = Character.toString(s.readChar());
			
			while(s.peek(IsChar.digit))
			{
				result += Character.toString(s.readChar());
			}
			ret = new NumLiteral(posInfo, Integer.parseInt(result));
		}
		else if(s.peek(IsChar.symbol))
		{
			posInfo = new PosInfo(s.FileName, s.Line, s.Col, s.Pos);
			String result = Character.toString(s.readChar());
			
			while(s.peek(IsChar.digit)|| s.peek(IsChar.symbol))
			{
				result += Character.toString(s.readChar());
			}
			ret = new Symbol(posInfo, result);
		}
		else if(s.peek("["))
		{
			posInfo = new PosInfo(s.FileName, s.Line, s.Col, s.Pos);
			VectorAst v = new VectorAst(posInfo);
			ret = v;
			s.nextChar();
			skipSpacing(s);
			while(peekfirstExpr(s))
			{
				AST a = read(s);
				if(a == null)
				{
					throw new ReaderException(posInfo, "Unexpected end of file");
				}
				skipSpacing(s);
				v.value.add(a);
			}
			if(!s.peek("]"))
			{
				throw new ReaderException(posInfo, "Expected ]");
			}
			s.readChar();
		}
		else if(s.peek("\""))
		{
			posInfo = new PosInfo(s.FileName, s.Line, s.Col, s.Pos);
			String result = "";
			s.readChar();
			while(true)
			{
				if(s.EOF())
				{
					throw new ReaderException(posInfo, "EOF before termination of string");
				}
				else if(s.peek("\""))
				{
					s.readChar();
					break;
				}
				else if(s.peek("\\"))
				{
					s.readChar();
					if(s.EOF())
					{
						throw new ReaderException(posInfo, "EOF during processing of string escape");
					}
					else if(s.peek("\\"))
					{
						result+= "\\";
						s.readChar();
					}
					else if(s.peek("\""))
					{
						result += "\"";
						s.readChar();
					}
					else
					{
						throw new ReaderException(posInfo, "Unknown escape: \\" + s.readChar());
					}
				}
				else
				{
					// normal char
					result += s.readChar();
				}
				
				
			}// end while
			ret = new StrLiteral(posInfo, result);
		}
		else
		{
			posInfo = new PosInfo(s.FileName, s.Line, s.Col, s.Pos);
			throw new ReaderException(posInfo, "Unexpected char:'" + s.readChar()+"'");
		}
		return ret;
	}
	private static boolean peekfirstExpr(Scanner s)
	{
		return s.peek("[") || s.peek(IsChar.digit) 
				|| s.peek(IsChar.symbol) || s.peek("\"");
	}
	private static void skipSpacing(Scanner s)
	{
		while(s.peek(IsChar.whiteSpace))
		{
			s.nextChar();
		}
	}
}
