package vm;

public abstract class NativeMethod
{
	public final int numArgs;
	public NativeMethod(int numArgs)
	{
		this.numArgs = numArgs;
	}
	public abstract Object run(Object[] args);
}
