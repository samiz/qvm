package vm;

import java.lang.reflect.Field;

import instructions.Instruction;
import instructions.InternalVmError;

public class JavaFieldGetter implements IMethod
{

	final private Field field;

	public JavaFieldGetter(Field f)
	{
		this.field = f;
	}

	@Override
	public Instruction apply(VM vm, Frame f1, boolean tail, Instruction next)
			throws InternalVmError
	{
		Object target = f1.operandStack.pop();
			try
			{
				f1.operandStack.push(field.get(target));
			}
			catch (IllegalArgumentException e)
			{
				throw new InternalVmError(
						String.format("Can't get field '%s' of object %s;%s",
								field.getName(), target));
			}
			catch (IllegalAccessException e)
			{
				throw new InternalVmError(
						String.format("Can't get field '%s' of object %s;%s",
								field.getName(), target));
			}
			return next;
	}

}
