package vm;

import java.lang.reflect.Field;

import instructions.Instruction;
import instructions.InternalVmError;

public class JavaFieldSetter implements IMethod
{

	final private Field field;

	public JavaFieldSetter(Field f)
	{
		this.field = f;
	}

	@Override
	public Instruction apply(VM vm, Frame f1, boolean tail, Instruction next)
			throws InternalVmError
	{
		Object val = f1.operandStack.pop();
		Object target = f1.operandStack.pop();
		try
		{
			field.set(target, val);
			return next;
		}
		catch (IllegalArgumentException e)
		{
			throw new InternalVmError(
					String.format("Can't set field '%s' of object %s;%s",
							field.getName(), target));
		}
		catch (IllegalAccessException e)
		{throw new InternalVmError(
				String.format("Can't set field '%s' of object %s;%s",
						field.getName(), target));
		}
	}

}
