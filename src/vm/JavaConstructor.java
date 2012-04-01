package vm;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import instructions.Instruction;
import instructions.InternalVmError;

public class JavaConstructor implements IMethod
{

	@SuppressWarnings("rawtypes")
	final private Constructor constuctor;
	final private int numArgs;

	@SuppressWarnings("rawtypes")
	public JavaConstructor(Constructor c, int numArgs)
	{
		this.constuctor = c;
		this.numArgs = numArgs;
	}

	@Override
	public Instruction apply(VM vm, Frame f1, boolean tail, Instruction next)
			throws InternalVmError
	{
		final Object[] args = new Object[this.numArgs];
		for (int i = this.numArgs - 1; i >= 0; i--)
		{
			args[i] = f1.operandStack.pop();
		}
		Object ret;
		try
		{
			ret = constuctor.newInstance(args);
			if (ret != null)
				f1.operandStack.push(ret);
		}
		catch (IllegalAccessException e)
		{
			throw new InternalVmError(String.format("Error calling Java constuctor '%s'; source:%s",
					constuctor.toString(), e.getMessage()));
		}
		catch (IllegalArgumentException e)
		{
			throw new InternalVmError(String.format("Error calling Java constuctor '%s'; source:%s",
					constuctor.toString(), e.getMessage()));
		}
		catch (InvocationTargetException e)
		{
			throw new InternalVmError(String.format("Error calling Java constuctor '%s'; source:%s",
					constuctor.toString(), e.getMessage()));
		}
		catch (InstantiationException e)
		{
			throw new InternalVmError(String.format("Error calling Java constuctor '%s'; source:%s",
					constuctor.toString(), e.getMessage()));
		}
		return next;
	}

}
