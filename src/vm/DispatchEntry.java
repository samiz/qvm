package vm;

public class DispatchEntry
{
	@SuppressWarnings("rawtypes")
	public Class[] types;
	public IMethod method;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean match(Class[] givenTypes)
	{
		if(types.length != givenTypes.length)
			return false;
		
		for(int i=0; i<types.length; i++)
		{
			if(!(types[i].isAssignableFrom(givenTypes[i])))
			{
				return false;
			}
		}
		return true;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean moreSpecificThan(DispatchEntry de2)
	{
		for(int i=0; i<types.length; i++)
		{
			Class c1 = types[i], c2 = de2.types[i];
			if(!c1.equals(c2) && c2.isAssignableFrom(c1))
				return true;
		}
		return false;
	}
}
