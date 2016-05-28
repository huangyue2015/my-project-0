package bio.threadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient
{

	public static void main(String[] args) throws IOException
	{
		int port = 8099;
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try
		{
			socket = new Socket("localhost", port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			out.println("QUERY TIME ORDER");
			out.flush();
			System.out.println("Send order server succeed");
			String resp = in.readLine();
			System.out.println("Now is:" + resp);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(socket != null)
				socket.close();
		}

	}

}
