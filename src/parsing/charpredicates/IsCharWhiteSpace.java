package parsing.charpredicates;

public class IsCharWhiteSpace implements IsChar
{

	@Override
	public boolean run(char c)
	{
		return Character.isWhitespace(c);
	}

}
