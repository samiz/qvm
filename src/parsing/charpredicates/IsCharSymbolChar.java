package parsing.charpredicates;

public class IsCharSymbolChar implements IsChar
{

	@Override
	public boolean run(char c)
	{
		return Character.isLetter(c) || c == '=' || c == '<' || c == '>'
				|| c == '!' || c == '&' || c == '|' || c == '%' || c == '+'
				|| c == '-' || c == '/' || c == '*' || c == '×' || c == '÷'
				|| c == '?' || c == '^' || c == '_' ;

		
	}

}
