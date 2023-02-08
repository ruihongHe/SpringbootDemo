package com.example.springboot.common.util;
/**
 * 线程池工具类
 * @author zlj
 * @date 2017年7月26日 下午4:25:13
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtils {
	//池中所保存的线程数，包括空闲线程。
	private static int corePoolSize = 10;
	private static String corePoolSizeKey = "threadPool.corePoolSize";
	//池中允许的最大线程数(采用LinkedBlockingQueue时没有作用)。
	private static int maximumPoolSize = 100;
	private static String maximumPoolSizeKey = "threadPool.maximumPoolSize";
	//当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间，线程池维护线程所允许的空闲时间。
	private static int keepAliveTime = 200;
	private static String keepAliveTimeKey = "threadPool.keepAliveTime";

	private static ExecutorService threadPool;
	/**
	 * 线程池单例
	 * @return
	 */
	private static ExecutorService threadPoolInstance(){
		if(threadPool != null){
			return threadPool;
		}else{
//			threadPool = Executors.newCachedThreadPool();
			threadPool = Executors.newFixedThreadPool(maximumPoolSize);
		}
		return threadPool;
	}
	/**
	 * 通过线程池执行一个线程
	 * @param rb
	 */
	public static void execute(Runnable rb){
		threadPoolInstance().execute(rb);
	}

}
