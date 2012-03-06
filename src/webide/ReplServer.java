package webide;

import interpreter.Interpreter;
import interpreter.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.json.simple.JSONObject;

public class ReplServer extends NanoHTTPD
{

	private String template;
	private final static String localDir = "./repl";
	Interpreter interpreter = new Interpreter();
	public ReplServer(int port, File wwwroot) throws IOException
	{
		super(port, wwwroot);
		template = Utils.loadFileContents(localDir+"/index.html");
		interpreter.initStdLib();
	}
	@SuppressWarnings("unchecked")
	public Response serve(String uri, 
			String method, 
			Properties header, 
			Properties parms, 
			Properties files)
	{
		try
		{
			template = Utils.loadFileContents(localDir+"/index.html");
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(uri.startsWith("/eval"))
		{
			String program = parms.getProperty("code");
			if(program == null)
				program = "";
			String output = "";
			try
			{
				if(program.trim()!="")
					interpreter.RunCode(program);
				Object val = interpreter.returnValue();
				if(val != null)
					output += val.toString();
			}
			catch (Exception e)
			{
				StackTraceElement[] els = e.getStackTrace();
				output += "<span style=\"color:red;\">" + e.toString() +"</span>";
				for(StackTraceElement el : els)
				{
					output += "<br/>&nbsp;&nbsp;&nbsp;at " + el.toString();
				}
				if(output.length() > 450)
					output = output.substring(0, 450) + "...";
				e.printStackTrace();
			}
			JSONObject res = new JSONObject();
			res.put("output", output);
			return new Response(HTTP_OK, MIME_JSON, res.toJSONString());
		}
		else if(uri.startsWith("/repl"))
		{
			String html = String.format(template);
			return new Response(HTTP_OK, MIME_HTML, html);
		}
		return super.serve(uri, method, header, parms, files);
	}
	public static void main( String[] args )
	{
		int port = 8080;
		try
		{
			new ReplServer(port, new File(localDir));
		}
		catch( IOException ioe )
		{
			System.err.println( "Couldn't start server:\n" + ioe );
			System.exit( -1 );
		}
		
		System.out.println( "Listening on port " + port +". Hit Enter to stop.\n" );
		try { System.in.read(); } catch( Throwable t ) {};
	}


}
