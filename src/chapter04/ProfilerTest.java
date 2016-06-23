package chapter04;

/**
 * Created by cdlvsheng on 2016/4/12.
 */
public class ProfilerTest {
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
		public Integer initialValue() {
			return 0;
		}
	};

	public int getNextNum() {
		seqNum.set(seqNum.get() + 1);
		return seqNum.get();
	}

	public static void main(String[] args) throws Exception {
		ProfilerTest pt = new ProfilerTest();
		A            a1 = new A(pt);
		A            a2 = new A(pt);
		A            a3 = new A(pt);

		new Thread(a1, "a1").start();
		new Thread(a2, "a2").start();
		new Thread(a3, "a3").start();

	}
}

class A implements Runnable {

	private ProfilerTest pt;

	public A(ProfilerTest pt) {
		this.pt = pt;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			int j = pt.getNextNum();
			System.out.println(Thread.currentThread().getName() + "\t pt : " + j);
		}
	}
}