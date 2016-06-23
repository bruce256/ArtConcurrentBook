package chapter06;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by cdlvsheng on 2016/4/11.
 */
public class BlockingQueueTest {
	public static void main(String[] args) {
		ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(100);
		try {
			for (int i = 0; i < 102; i++) {
				System.out.println(q.offer(new Integer(i), 1, TimeUnit.SECONDS));
			}

			System.out.println(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
