package br.com.gginez.thread_pools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutor {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("This is the main Thread:" + Thread.currentThread().getId());
		
		int[][] a = new int[2][2];
		
		a[0][0] = 1;
		a[0][1] = 1;
		a[1][0] = 1;
		a[1][1] = 1;
		
		int[][] b = new int[2][2];
		
		b[0][0] = 1;
		b[0][1] = 1;
		b[1][0] = 1;
		b[1][1] = 1;
		
//		ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
//		executor.schedule(() -> {
//			int[][] result = MatrixOperations.sum(a, b);
//			MatrixOperations.printMatrix(result, "Resultado");
//		}, 5000, TimeUnit.MILLISECONDS);
//		
//		Thread.sleep(6000);
//		executor.shutdown();
		
		CountDownLatch lock = new CountDownLatch(15);
		 
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
		ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
			int[][] result = MatrixOperations.sum(a, b);
			MatrixOperations.printMatrix(result, "Resultado");
		    lock.countDown();
		}, 2000, 500, TimeUnit.MILLISECONDS);
		 
		lock.await(10000, TimeUnit.MILLISECONDS);
		future.cancel(true);
	}
	
}
