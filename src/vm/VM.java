package vm;

import interpreter.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class VM
{
	public final Map<String, Map<Integer, List<DispatchEntry>>> dispatchn = 
			new HashMap<String, Map<Integer,List<DispatchEntry>>>();
	public final Map<String, Object> constantPool = new HashMap<String, Object>();
	public final Map<String, Object> globalEnvironment = new HashMap<String, Object>();
	Queue<QProcess> runQueue = new LinkedList<QProcess>();
	Queue<QProcess> timerQueue = new LinkedList<QProcess>();
	Queue<QProcess> inputQueue = new LinkedList<QProcess>();
	public QProcess currentProcess;
	public Frame _currentFrame;
	public boolean halted;

	public final Frame currentFrame()
	{
		return _currentFrame;
	}
	public QProcess schedule()
	{
		if (!timerQueue.isEmpty())
			return rotateProcess(timerQueue);
		if (!inputQueue.isEmpty())
			return rotateProcess(inputQueue);
		if (!runQueue.isEmpty())
			return rotateProcess(runQueue);
		halted = true;
		return null;
	}

	private QProcess rotateProcess(Queue<QProcess> queue)
	{
		QProcess p = queue.poll();
		queue.add(p);
		_currentFrame = p.callStack().peek();
		return p;
	}

	public void run() throws VmException
	{
		Random r = new Random();
		int rnd;
		while (!halted)
		{
			rnd = r.nextInt(30);
			while (!halted && rnd-- > 0)
			{
				currentFrame().currentInstruction = currentFrame().currentInstruction.run(this);
			}

			if (!halted)
				currentProcess = schedule();

		}
	}

	public Object topMostValue()
	{
		return currentFrame().operandStack.peek();
	}

	@SuppressWarnings("rawtypes")
	public void registerMethod(String sym, Class[] types, Object method)
	{
		constantPool.put(sym, method);
		final int nargs = types.length;
		if (!dispatchn.containsKey(sym))
			dispatchn.put(sym, new HashMap<Integer, List<DispatchEntry>>());
		
		
		if(!dispatchn.get(sym).containsKey(nargs))
			dispatchn.get(sym).put(nargs, new ArrayList<DispatchEntry>());
		List<DispatchEntry> list = dispatchn.get(sym).get(nargs);

		DispatchEntry de = new DispatchEntry();
		de.types = types;
		de.method = method;

		boolean added = false;
		for (int i = 0; i < list.size(); i++)
		{
			DispatchEntry de2 = list.get(i);
			if (de.moreSpecificThan(de2))
			{
				list.add(i, de);
				added = true;
				break;
			}
		}
		if (!added)
			list.add(de);

	}

	@SuppressWarnings("rawtypes")
	public Object dispatchn(String sym, Class[] types) throws NoMethodException
	{
		if(!dispatchn.containsKey(sym))
			throw new NoMethodException("Method not registered:"+ sym);
		if(!dispatchn.get(sym).containsKey(types.length))
			throw new NoMethodException(String.format(
					"Method '%s' not registered with arity of %s",sym, types.length));
				
		List<DispatchEntry> entries = dispatchn.get(sym).get(types.length);
		for (DispatchEntry de : entries)
		{
			if (de.match(types))
				return de.method;
		}
		throw new NoMethodException("No method: " + sym + " for "
				+ Utils.toString(types));
	}

	public void pushMainMethod()
	{
		QProcess proc = new QProcess();
		proc.callStack().push(
				new Frame((Method) constantPool.get("main")));
		runQueue.clear();
		runQueue.add(proc);
		currentProcess = schedule();
	}
}