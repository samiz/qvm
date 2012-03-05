package parsing;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReaderTest
{

	@Test
	public void testReadNumlit() throws ReaderException
	{
		assertEquals("12", Reader.read("12", "").toString());
	}
	@Test
	public void testReadStr() throws ReaderException
	{
		assertEquals("hello", Reader.read("\"hello\"   ", "").toString());
	}
	@Test
	public void testReadSymbol() throws ReaderException
	{
		assertEquals("asymbol", Reader.read("asymbol", "").toString());
	}
	@Test
	public void testReadSymbolAndGarbage() throws ReaderException
	{
		try
		{
			Reader.read("x;", "").toString();
			fail("Should have thrown a Reader Exception/Garbage at end of input");
		}
		catch(ReaderException ex)
		{
			assertTrue(ex.getMessage().contains("Garbage at end of program"));
		}
	}
	@Test
	public void testReadList() throws ReaderException
	{
		assertEquals("[x hello ]", Reader.read("[x \"hello\"  ]", "").toString());
	}
	@Test
	public void testReadListAndGarbage() throws ReaderException
	{
		try
		{
			Reader.read("[x ][", "").toString();
			fail("Should have thrown a Reader Exception/Garbage at end of input");
		}
		catch(ReaderException ex)
		{
			assertTrue(ex.getMessage().contains("Garbage at end of program"));
		}
	}
	@Test
	public void testReadListNoRightParen() throws ReaderException
	{
		try
		{
			Reader.read("[x ", "").toString();
			fail("Should have thrown a Reader Exception/Missing ]");
		}
		catch(ReaderException ex)
		{
			assertTrue(ex.getMessage().contains("Expected ]"));
		}
	}
	@Test
	public void testReadEmptyList() throws ReaderException
	{
			assertEquals("[]", Reader.read(" [ ]  ", "").toString());
	}
	@Test
	public void testReadNestedList() throws ReaderException
	{
			assertEquals("[+ 4 [- 5 6 ] ]", Reader.read("[+ 4 [- 5 6 ] ]", "").toString());
	}
}
