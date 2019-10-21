package br.com.gginez.thread_pools;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ListenerExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

		ListenableFuture<String> future1 = 
		  listeningExecutorService.submit(() -> "Hello");
		ListenableFuture<String> future2 = 
		  listeningExecutorService.submit(() -> "World");

		String greeting = Futures.allAsList(future1, future2).get()
		  .stream()
		  .collect(Collectors.joining(" "));
		System.out.println(greeting);
	}
	
}
