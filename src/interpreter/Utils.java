package interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class Utils
{
	public static final boolean debug = true;

	@SuppressWarnings("rawtypes")
	public static Class[] repeatClass(Class value, int n)
	{
		Class[] ret = new Class[n];
		for (int i = 0; i < n; i++)
			ret[i] = value;
		return ret;
	}

	public static <T> String toString(T[] vals)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (T val : vals)
		{
			sb.append(val.toString());
			sb.append(", ");
		}
		sb.append("}");
		return sb.toString();
	}

	public static String loadFileContents(String fname) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		String line;
		Reader r = new InputStreamReader(new FileInputStream(fname), "UTF-8");

		BufferedReader br = new BufferedReader(r);
		while ((line = br.readLine()) != null)
		{
			sb.append(line);
			sb.append("\n");
		}
		br.close();
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public static Class[] concat(Class theClass, Class<?>[] types)
	{
		Class[] arr = new Class[1 + types.length];
		arr[0] = theClass;
		for (int i = 0; i < types.length; i++)
			arr[i + 1] = types[i];
		return arr;
	}

	static Set<String> definedClasses = new HashSet<String>(); 
	@SuppressWarnings("rawtypes")
	public static Class createNewClass(String cname, String parentName,
			List<String> fieldNames)
	{
		if(definedClasses.contains(cname))
			return null;
		definedClasses.add(cname);
		ClassWriter cw = new ClassWriter(0);

		cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC, cname, null, parentName,
				new String[] {});
		Type obj = Type.getType(Object.class);
		for (String fn : fieldNames)
		{
			cw.visitField(Opcodes.ACC_PUBLIC, fn, obj.getDescriptor(), null,
					null).visitEnd();
		}

		// generate the default constructor
        MethodVisitor mv =
            cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, parentName,
            "<init>", "()V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        // end default constructor

		cw.visitEnd();
		byte[] b = cw.toByteArray();
		return Utils.myClassLoader.defineClass(cname, b);
	}

	static MyClassLoader myClassLoader = new MyClassLoader();
}
