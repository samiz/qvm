package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Type;

import ast.AST;
import ast.VectorAst;
import instructions.*;
import parsing.Reader;
import parsing.ReaderException;
import vm.Method;
import vm.NoMethodException;
import vm.VM;
import vm.VmException;
import vm.nativemethods.*;

public class Interpreter
{
	public VM vm = new VM();
	final private Map<String, Macro> macros = new HashMap<String, Macro>();

	public void RunCode(String program) throws ReaderException, VmException,
			InterpreterException
	{
		AST tree = Reader.read("[" + program + "]", "<untitled>");
		decodeInstructions((VectorAst) tree, vm);
		vm.pushMainMethod();
		vm.halted = false;
		vm.run();
	}

	public void initStdLib()
	{
		vm.registerMethod("+", new Class[]
		{ Integer.class, Integer.class }, new AddIntInt());
		vm.registerMethod("+", new Class[]
		{ Double.class, Double.class }, new AddDoubleDouble());
		vm.registerMethod("+", new Class[]
		{ String.class, String.class }, new AddStringString());
		vm.registerMethod("-", new Class[]
		{ Integer.class, Integer.class }, new SubtractIntInt());
		vm.registerMethod("-", new Class[]
		{ Double.class, Double.class }, new SubtractDoubleDouble());
		vm.registerMethod("*", new Class[]
				{ Integer.class, Integer.class }, new MulIntInt());
		vm.registerMethod("*", new Class[]
				{ Double.class, Double.class }, new MulDoubleDouble());
		vm.registerMethod("==", new Class[]
				{ Object.class, Object.class }, new EqObjectObject());
		vm.registerMethod("!=", new Class[]
				{ Object.class, Object.class }, new NotEqObjectObject());
		vm.registerMethod("<", new Class[]
				{ Comparable.class, Comparable.class }, new Lt());
		vm.registerMethod("<=", new Class[]
				{ Comparable.class, Comparable.class }, new LE());
		vm.registerMethod(">", new Class[]
				{ Comparable.class, Comparable.class }, new Gt());
		vm.registerMethod("<=", new Class[]
				{ Comparable.class, Comparable.class }, new GE());
		vm.registerMethod("&&", new Class[]
				{ Boolean.class, Boolean.class }, new And());
		vm.registerMethod("||", new Class[]
				{ Boolean.class, Boolean.class }, new Or());
		vm.registerMethod("!", new Class[]
				{ Boolean.class}, new Not());
		vm.registerMethod("print", new Class[]
		{ Object.class }, new PrintObject());
	}

	private void decodeInstructions(VectorAst treeVector, VM avm)
			throws NonDecodableInstructionException, VmException
	{
		for (AST tree : treeVector.value)
		{
			decodeDirective(tree, avm);
		}
	}

	@SuppressWarnings("rawtypes")
	private void decodeDirective(AST tree, VM avm)
			throws NonDecodableInstructionException, VmException,
			NoMethodException
	{
		if (tree.matchSymAt(0, "method"))
		{
			String mname = tree.symbolAt(1);
			Method m = decodeMethod(mname, tree.intAt(2), tree.VectorAt(3),
					avm);
			Class[] types = Utils.repeatClass(Object.class, m.numArgs);
			vm.registerMethod(mname, types, m);
		}
		else if(tree.matchSymAt(0, "macro"))
		{
			String mname = tree.symbolAt(1);
			Method m = decodeMethod(mname, tree.intAt(2), tree.VectorAt(3),
					avm);
			Class[] types = Utils.repeatClass(Object.class, m.numArgs);
			vm.registerMethod(mname, types, m);
			this.registerMacro(mname, types);
		}
		else if(tree.matchSymAt(0, "class"))
		{
			String cname = tree.symbolAt(1);
			
			String parentName = tree.stringAt(2);
			Class parentClass;
			try
			{
				parentClass = Class.forName(parentName);
			}
			catch (ClassNotFoundException e)
			{
				throw new VmException(String.format(
						"Cannot find parent class '%s' while defining class '%s'",
						parentName, cname));
			}
			Type typ = Type.getType(parentClass);
			parentName = typ.getInternalName();
			List<String> fieldNames = new ArrayList<String>();
			for(int i=3; i<tree.countElems();i++)
			{
				fieldNames.add(tree.symbolAt(i));
			}
			Class cls = Utils.createNewClass(cname, parentName, fieldNames);
			if(cls != null)
				avm.registerJavaClass(cls);
		}
		else
		{
			String head = tree.symbolAt(0);
			if(macros.containsKey(head))
			{
				Macro m = macros.get(head);
				VectorAst args = tree.tail();
				VectorAst expansion = expandMacro((Method) avm.dispatchn(head, m.argTypes), args);
				System.out.println(String.format("Expasnsion of macro: %s is %s", head, expansion.toString()));
				decodeDirective(expansion, avm);
			}
		}
	}

	private VectorAst expandMacro(Method method, VectorAst args) throws VmException
	{
		VectorAst result = (VectorAst) vm.createSoleProcess(method, args);
		
		return result;
	}

	@SuppressWarnings("rawtypes")
	private void registerMacro(String mname, Class[] types)
	{
		this.macros.put(mname, new Macro(mname, types));
	}

	private Method decodeMethod(String name, int arity, VectorAst instructions,
			VM avm) throws NonDecodableInstructionException
	{
		Method m = new Method(arity);
		for (AST i : instructions.value)
		{
			if (i.matchSymAt(0, "label"))
			{
				String lbl = i.symbolAt(1);
				Instruction inst;
				if (i.countElems() < 3)
					inst = new Nop().meta(lbl);
				else
					inst = decodeInstruction(i.at(2), avm);
				m.labels.put(lbl, inst);
				m.addInstruction(inst);
			}
			else
			{
				m.addInstruction(decodeInstruction(i, avm));
			}
		}
		return m;
	}

	private Instruction decodeInstruction(AST i, VM avm)
			throws NonDecodableInstructionException
	{
		if (i.matchSymAt(0, "pushl"))
		{
			return new PushL(i.symbolAt(1)).meta(i);
		}
		else if (i.matchSymAt(0, "popl"))
		{
			return new PopL(i.symbolAt(1)).meta(i);
		}
		else if (i.matchSymAt(0, "pushg"))
		{
			return new PushG(i.symbolAt(1)).meta(i);
		}
		else if (i.matchSymAt(0, "popg"))
		{
			return new PopG(i.symbolAt(1)).meta(i);
		}
		else if (i.matchSymAt(0, "pushv"))
		{
			Object val;
			AST arg = i.at(1);
			if (arg.matchSym("true"))
				val = true;
			else if (arg.matchSym("false"))
				val = false;
			else
				val = arg.eval();
			return new PushV(val).meta(i);
		}
		else if (i.matchSymAt(0, "pushq"))
		{
			AST arg = i.at(1);
			return new PushQuote(arg).meta(i);
		}
		else if (i.matchSymAt(0, "halt"))
		{
			return new Halt().meta(i);
		}
		else if (i.matchSymAt(0, "ret"))
		{
			return new Ret().meta(i);
		}
		else if (i.matchSymAt(0, "ifnot"))
		{
			return new IfNot(i.symbolAt(1)).meta(i);
		}
		else if (i.matchSymAt(0, "jmp"))
		{
			return new Jmp(i.symbolAt(1)).meta(i);
		}
		else if (i.matchSymAt(0, "nop"))
		{
			return new Nop().meta(i);
		}
		else if (i.matchSymAt(0, "import"))
		{
			return new Import(i.at(1).toString()).meta(i);
		}
		else if (i.matchSymAt(0, "dispatch"))
		{
			return new Dispatch(i.symbolAt(1), i.intAt(2)).meta(i);
		}
		else if (i.matchSymAt(0, "apply"))
		{
			boolean tail = i.matchSymAt(1, "tail");
			return new Apply(tail).meta(i);
		}
		throw new NonDecodableInstructionException(i);
	}

	public Object returnValue() throws InternalVmError
	{
		return vm.topMostValue();
	}
}
