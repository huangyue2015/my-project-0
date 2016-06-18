package thread;

import java.util.LinkedList;
import java.util.Queue;

public class VolatileTest
{
	private static volatile Queue<Ticket> tickets = new LinkedList<>();

	public void init()
	{
		for(int i = 0; i < 10000; i++)
		{
			tickets.offer(new Ticket(i+"", 0));
		}
	}
	
	public static void main(String[] args)
	{
		new VolatileTest().init();
		for (int i = 0; i < 1150; i++)
		{
			new Thread(new VolatileFeaturesExample() ).start();
		}
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	static class VolatileFeaturesExample implements Runnable
	{
		@Override
		public void run()
		{
			if(!tickets.isEmpty())
			{
				Ticket tt = tickets.poll();
				System.out.println(tickets.size());
				if(tt != null)
					System.out.println(tt.getNum());
			}
		}
		
	}
}

class Ticket
{
	private String num;
	private int state;
	public String getNum()
	{
		return num;
	}
	public void setNum(String num)
	{
		this.num = num;
	}
	public int getState()
	{
		return state;
	}
	public void setState(int state)
	{
		this.state = state;
	}
	
	public Ticket(String num, int state)
	{
		super();
		this.num = num;
		this.state = state;
	}
}















