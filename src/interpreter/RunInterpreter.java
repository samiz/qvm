package interpreter;

public class RunInterpreter
{

	public static void main(String[] args)
	{
		Interpreter interpreter = new Interpreter();
		try
		{
			interpreter.initStdLib();
			interpreter.RunCode("[method main 0 [[pushv 12] [dispatch print 1] [applyNative][halt]]]");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
