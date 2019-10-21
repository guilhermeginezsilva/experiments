package br.com.gginez.thread_pools;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SingleThreaded {

	public static void main(String[] args) {
		System.out.println("This is the main Thread:" + Thread.currentThread().getId());
		
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(() -> System.out.println("This is the executor Thread:" + Thread.currentThread().getId()));
	}
	
}
