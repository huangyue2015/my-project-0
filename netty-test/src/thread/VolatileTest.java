package thread;

public class VolatileTest
{
	static volatile long vl = 0L; // 使用volatile声明64位的long型变量

	public void set(long l)
	{
		vl = l; // 单个volatile变量的写
	}

	public void getAndIncrement()
	{
		vl++; // 复合（多个）volatile变量的读/写
	}

	public long get()
	{
		return vl; // 单个volatile变量的读
	}
	
	public static void main(String[] args)
	{

		// 同时启动1000个线程，去进行i++计算，看看实际结果

		for (int i = 0; i < 1001; i++)
		{
			new Thread(new VolatileFeaturesExample((long) i)).start();
		}
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		// 这里每次运行的值都有可能不同,可能为1000
		System.out.println("运行结果:Counter.count=" + new VolatileTest().get());
	}
	
	static class VolatileFeaturesExample implements Runnable
	{
		private long i;
		
		public VolatileFeaturesExample(Long i)
		{
			this.i = i;
		}
		
		@Override
		public void run()
		{
			new VolatileTest().set(i);
		}
		
	}
}
