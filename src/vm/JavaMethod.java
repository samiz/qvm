package vm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import instructions.Instruction;
import instructions.InternalVmError;

public class JavaMethod implements IMethod
{

	final private Method method;
	final private int numArgs;

	public JavaMethod(Method m, int numArgs)
	{
		this.method = m;
		this.numArgs = numArgs;
	}

	@Override
	public Instruction apply(VM vm, Frame f1, boolean tail, Instruction next)
			throws InternalVmError
	{
		final Object[] args = new Object[this.numArgs-1];
		for (int i = this.numArgs - 2; i >= 0; i--)
		{
			args[i] = f1.operandStack.pop();
		}
		Object target = f1.operandStack.pop();
		Object ret;
		try
		{
			ret = method.invoke(target, args);
			if (ret != null)
				f1.operandStack.push(ret);
		}
		catch (IllegalAccessException e)
		{
			throw new InternalVmError(String.format("Error calling Java method '%s'; source:%s",
					method.toString(), e.getMessage()));
		}
		catch (IllegalArgumentException e)
		{
			throw new InternalVmError(String.format("Error calling Java method '%s'; source:%s",
					method.toString(), e.getMessage()));
		}
		catch (InvocationTargetException e)
		{
			throw new InternalVmError(String.format("Error calling Java method '%s'; source:%s",
					method.toString(), e.getMessage()));
		}
		
		return next;
	}

}
