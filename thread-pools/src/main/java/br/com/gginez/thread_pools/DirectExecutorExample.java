package br.com.gginez.thread_pools;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.util.concurrent.MoreExecutors;

public class DirectExecutorExample {

	public static void main(String[] args) {
		Executor executor = MoreExecutors.directExecutor();
		 
		AtomicBoolean executed = new AtomicBoolean();
		 
		executor.execute(() -> {
		    try {
		        Thread.sleep(2000);
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    executed.set(true);
		});
		System.out.println(executed.get());
	}
	
}
