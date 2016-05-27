package bio.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExcutePool
{
	private ExecutorService executorService;
	
	public TimeServerHandlerExcutePool(int maxPoolSize, int queueSize)
	{
		executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
		
	}
	
	public void  excute(Runnable task)
	{
		executorService.execute(task);
	}
}
