package interpreter;

public class Macro 
{

	final public String name;
	@SuppressWarnings("rawtypes")
	final public Class[] argTypes;

	@SuppressWarnings("rawtypes")
	public Macro(String mname, Class[] types)
	{
		this.name = mname;
		this.argTypes = types;
	}

}
