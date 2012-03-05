package interpreter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import parsing.ReaderException;
import vm.VmException;

public class InterpreterTest
{

	Interpreter interpreter;
	@Before
	public void setUp() throws Exception
	{
		interpreter = new Interpreter();
		interpreter.initStdLib();
	}

	@Test
	public final void testIfTrue() throws ReaderException, VmException, InterpreterException
	{
		/*
	 [method main 0 [
     [pushv true]
     [ifnot haw]
     [pushv "naw"]
     [jmp end]
     [label haw]
     [pushv "haw"]
     [label end]
     [halt]
	]]
		 */
		String code ="[method main 0 [\r\n" + 
				"     [pushv true]\r\n" + 
				"     [ifnot haw]\r\n" + 
				"     [pushv \"naw\"]\r\n" + 
				"     [jmp end]\r\n" + 
				"     [label haw]\r\n" + 
				"     [pushv \"haw\"]\r\n" + 
				"     [label end]\r\n" + 
				"     [halt]\r\n" + 
				"	]]";
		interpreter.RunCode(code);
		String ret = interpreter.returnValue().toString();
		assertEquals("naw", ret);
	}
	@Test
	public final void testIfFalse() throws ReaderException, VmException, InterpreterException
	{
		/*
	 [method main 0 [
     [pushv false]
     [ifnot haw]
     [pushv "naw"]
     [jmp end]
     [label haw]
     [pushv "haw"]
     [label end]
     [halt]
	]]
		 */
		String code ="[method main 0 [\r\n" + 
				"     [pushv false]\r\n" + 
				"     [ifnot haw]\r\n" + 
				"     [pushv \"naw\"]\r\n" + 
				"     [jmp end]\r\n" + 
				"     [label haw]\r\n" + 
				"     [pushv \"haw\"]\r\n" + 
				"     [label end]\r\n" + 
				"     [halt]\r\n" + 
				"	]]";
		interpreter.RunCode(code);
		String ret = interpreter.returnValue().toString();
		assertEquals("haw", ret);
	}

}
