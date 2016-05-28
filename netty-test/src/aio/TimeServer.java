package aio;

public class TimeServer
{

	public static void main(String[] args)
	{
		new Thread(new AsyncTimeServerHandler(8099)).start();;
	}

}
