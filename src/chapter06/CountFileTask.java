package chapter06;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by cdlvsheng on 2016/4/4.
 */
public class CountFileTask extends RecursiveTask<Integer> {
	private File file;

	public CountFileTask(File f) {
		this.file = f;
	}

	@Override
	protected Integer compute() {
		int    sum  = 0;
		File[] list = file.listFiles();

		for (File f : list) {
			if (f.isDirectory()) {
				CountFileTask cft = new CountFileTask(f);
				cft.fork();
				sum += cft.join();
			} else sum++;
		}

		return sum;
	}

	public static void main(String[] args) {
		ForkJoinPool    forkJoinPool = new ForkJoinPool();
		CountFileTask   task         = new CountFileTask(new File("D:\\Code_Git"));
		long            start        = System.currentTimeMillis();
		Future<Integer> result       = forkJoinPool.submit(task);
		try {
			System.out.println(result.get());

			long duration = System.currentTimeMillis() - start;
			System.out.println("time cost: " + duration + " ms");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
