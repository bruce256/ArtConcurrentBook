/**
 *
 */
package chapter04;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 6-11
 */
public class WaitNotify {
	private static final SimpleDateFormat sdf  = new SimpleDateFormat("HH:mm:ss");
	static               boolean          flag = true;
	static               Object           lock = new Object();

	public static void main(String[] args) {
		Thread waitThread = new Thread(new Wait(), "WaitThread");
		waitThread.start();
		sleepSeconds(1);

		Thread notifyThread = new Thread(new Notify(), "NotifyThread");
		notifyThread.start();
	}

	static class Wait implements Runnable {
		public void run() {
			synchronized (lock) {
				// 当条件不满足时，继续wait，同时释放了lock的锁
				while (flag) {
					try {
						System.out.println(CuttentThreadName() + " flag is true. wait @ " + sdf.format(new Date()));
						lock.wait();
						System.out.println(CuttentThreadName() + " gained lock again. wait @ " + sdf.format(new Date()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 条件满足时，完成工作
				System.out.println(CuttentThreadName() + " flag is false. running @ " + sdf.format(new Date()));
			}
		}
	}

	private static String CuttentThreadName() {
		return Thread.currentThread().getName();
	}

	static class Notify implements Runnable {
		@Override
		public void run() {
			// 加锁，拥有lock的Monitor
			synchronized (lock) {
				// 获取lock的锁，然后进行通知，通知时不会释放lock的锁，
				// 直到当前线程释放了lock后，WaitThread才能从wait方法中返回
				System.out.println(CuttentThreadName() + " hold lock. notify @ " + sdf.format(new Date()));
				lock.notifyAll();
				flag = false;
				sleepSeconds(5);
				System.out.println(CuttentThreadName() + " is releasing lock  @ " + sdf.format(new Date()));
			}
			// 再次加锁
			synchronized (lock) {
				System.out.println(CuttentThreadName() + " hold lock again. sleep @ " + sdf.format(new Date()));
				sleepSeconds(5);
				System.out.println(CuttentThreadName() + " is releasing lock  @ " + sdf.format(new Date()));
			}
		}
	}

	private static void sleepSeconds(int timeout) {
		try {
			TimeUnit.SECONDS.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
