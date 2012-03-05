package vm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import vm.nativemethods.AddDoubleDouble;
import vm.nativemethods.AddIntInt;
import vm.nativemethods.AddStringString;

public class TestDispatch
{
	VM vm;
	NativeMethod 
		addIntInt, addDoubleDouble, addStringString;
	@Before
	public void setUp() throws Exception
	{
		vm = new VM();
		addIntInt = new AddIntInt();
		addDoubleDouble = new AddDoubleDouble();
		addStringString = new AddStringString();
		
		vm.registerMethod("+", 
				new Class[]{Integer.class, Integer.class},
				addIntInt);
		vm.registerMethod("+", 
				new Class[]{Double.class, Double.class},
				addDoubleDouble);
		vm.registerMethod("+", 
				new Class[]{String.class, String.class},
				addStringString);
		
	}

	@Test
	public final void testAddIntInt() throws NoMethodException
	{
		assertEquals(addIntInt, 
				vm.dispatchn("+", new Class[] {Integer.class, Integer.class}));
	}
	@Test
	public final void testAddStringString() throws NoMethodException
	{
		assertEquals(addStringString, 
				vm.dispatchn("+", new Class[] {String.class, String.class}));
	}
	@Test
	public final void testAddStringInt()
	{
		try
		{
			vm.dispatchn("+", new Class[] {String.class, Integer.class});
			fail("Should have thrown a NoMethodException");
		}
		catch(NoMethodException ex)
		{
			assertTrue(true);
		}
	}
}
