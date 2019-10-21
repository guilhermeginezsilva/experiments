package br.com.gginez.thread_pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.MoreExecutors;

public class ExitingExecutorServices {

	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		ExecutorService executorService = MoreExecutors.getExitingExecutorService(executor, 
		    100, TimeUnit.MILLISECONDS);
		 
		executorService.submit(() -> {
		    while (true) {
		    }
		});
	}
	
}
