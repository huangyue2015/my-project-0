package com.common.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Globle {

	private static ExecutorService executorService;
	
	static
	{
		executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 100, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5000));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	

	public static void  excute(Runnable task)
	{
		executorService.execute(task);
	}
}
