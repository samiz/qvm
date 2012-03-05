package parsing.charpredicates;

public interface IsChar
{
	public static final IsChar digit = new IsCharDigit();
	public static final IsChar symbol = new IsCharSymbolChar();
	public static final IsChar whiteSpace = new IsCharWhiteSpace();
	boolean run(char c);
}
