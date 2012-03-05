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
	public final Map<String, List<DispatchEntry>> dispatchn = new HashMap<>();
	public final Map<String, Object> constantPool = new HashMap<>();
	public final Map<String, Object> globalEnvironment = new HashMap<>();
	Queue<QProcess> runQueue = new LinkedList<>();
	Queue<QProcess> timerQueue = new LinkedList<>();
	Queue<QProcess> inputQueue = new LinkedList<>();
	public QProcess currentProcess;
	public boolean halted;

	public final Frame currentFrame()
	{
		return currentProcess.callStack().peek();
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
		return p;
	}

	public void run() throws VmException
	{
		Random r = new Random();
		Frame f;
		int rnd;
		while (!halted)
		{
			f = currentFrame();
			rnd = r.nextInt(30);
			while (!halted && rnd-- > 0)
			{
				f.currentInstruction = f.currentInstruction.run(this);
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
		if (!dispatchn.containsKey(sym))
			dispatchn.put(sym, new ArrayList<DispatchEntry>());
		List<DispatchEntry> list = dispatchn.get(sym);

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
		List<DispatchEntry> entries = dispatchn.get(sym);
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