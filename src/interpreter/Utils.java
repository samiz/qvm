package interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Utils
{
	public static final boolean debug = true;
	@SuppressWarnings("rawtypes")
	public static Class[] repeatClass(Class value, int n)
	{
		Class[] ret = new Class[n];
		for(int i=0; i<n; i++)
			ret[i] = value;
		return ret;
	}

	public static<T> String toString(T[] vals)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(T val : vals)
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
		Reader r = new InputStreamReader(
				new FileInputStream(fname), "UTF-8");
		
		BufferedReader br = new BufferedReader(r);
		while((line = br.readLine()) != null)
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
		Class[] arr = new Class[1+ types.length];
		arr[0] = theClass;
		for(int i=0;i<types.length; i++)
			arr[i+1] = types[i];
		return arr ;
	}

}
