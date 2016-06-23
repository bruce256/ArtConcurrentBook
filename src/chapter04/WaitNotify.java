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
				// ������������ʱ������wait��ͬʱ�ͷ���lock����
				while (flag) {
					try {
						System.out.println(CuttentThreadName() + " flag is true. wait @ " + sdf.format(new Date()));
						lock.wait();
						System.out.println(CuttentThreadName() + " gained lock again. wait @ " + sdf.format(new Date()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// ��������ʱ����ɹ���
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
			// ������ӵ��lock��Monitor
			synchronized (lock) {
				// ��ȡlock������Ȼ�����֪ͨ��֪ͨʱ�����ͷ�lock������
				// ֱ����ǰ�߳��ͷ���lock��WaitThread���ܴ�wait�����з���
				System.out.println(CuttentThreadName() + " hold lock. notify @ " + sdf.format(new Date()));
				lock.notifyAll();
				flag = false;
				sleepSeconds(5);
				System.out.println(CuttentThreadName() + " is releasing lock  @ " + sdf.format(new Date()));
			}
			// �ٴμ���
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
