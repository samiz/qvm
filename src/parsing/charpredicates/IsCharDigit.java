package parsing.charpredicates;

class IsCharDigit implements IsChar
{

	@Override
	public boolean run(char c)
	{
		return Character.isDigit(c);
	}

}
