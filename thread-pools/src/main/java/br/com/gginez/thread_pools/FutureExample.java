package br.com.gginez.thread_pools;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {
	
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
		
		
		ExecutorService executorService = Executors.newFixedThreadPool(8);
		Future<int[][]> future = executorService.submit(() -> {
			Thread.sleep(5000);
			return MatrixOperations.sum(a, b);
		});
		// some operations
		while(!future.isDone()) {
			System.out.println("Rodando");
			Thread.sleep(500);
		}
		int[][] result = future.get();
		MatrixOperations.printMatrix(result, "Resultado");
		executorService.shutdown();
	}
	
}
