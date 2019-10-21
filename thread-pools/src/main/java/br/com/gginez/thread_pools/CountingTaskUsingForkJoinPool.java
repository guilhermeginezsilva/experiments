package br.com.gginez.thread_pools;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

public class CountingTaskUsingForkJoinPool extends RecursiveTask<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1043714977251040819L;
	private final TreeNode node;
	 
    public CountingTaskUsingForkJoinPool(TreeNode node) {
        this.node = node;
    }
 
    @Override
    protected Integer compute() {
        return node.value + node.children.stream()
          .map(childNode -> {
        	  System.out.println(Thread.currentThread().getId());
        	  return new CountingTaskUsingForkJoinPool(childNode).fork();
          })
          .collect(Collectors.summingInt(ForkJoinTask::join));
    }
	
	static class TreeNode {
		 
	    int value;
	 
	    Set<TreeNode> children;
	 
	    TreeNode(int value, TreeNode... children) {
	        this.value = value;
	        this.children = Sets.newHashSet(children);
	    }
	}
	
	public static void main(String[] args) {
		TreeNode tree = new TreeNode(5,
				  new TreeNode(3), new TreeNode(2,
				    new TreeNode(2), new TreeNode(8)));
				 
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		int sum = forkJoinPool.invoke(new CountingTaskUsingForkJoinPool(tree));
		System.out.println("Result:" + sum);
	}
	
}
