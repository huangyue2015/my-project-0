package aio;

import java.io.IOException;

public class TimeClient
{

	public static void main(String[] args)
	{
		try
		{
			new Thread(new AsyncTimeClientHandler("localhost", 8099)).start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
