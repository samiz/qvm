package interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Utils
{

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

}