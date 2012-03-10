package instructions;

import interpreter.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import vm.JavaConstructor;
import vm.JavaMethod;
import vm.VM;
import vm.VmException;

public class Import extends Instruction
{
	private final String className;
	public Import(String className)
	{
		this.className = className;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Instruction run(VM vm) throws VmException
	{
		Class theClass;
		try
		{
			theClass = Class.forName(className);
			for(Method m : theClass.getMethods())
			{
				Class[] types = Utils.concat(theClass, m.getParameterTypes());
				vm.registerMethod(m.getName(), types, new JavaMethod(m, m.getParameterTypes().length+1));
			}
			for(Constructor c : theClass.getConstructors())
			{
				Class[] types = c.getParameterTypes();
				vm.registerMethod(theClass.getSimpleName(), types, new JavaConstructor(c, c.getParameterTypes().length));
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new VmException("Cannot load class: "+ className +"/" + e.getMessage());
		}
		return next;
	}

}
