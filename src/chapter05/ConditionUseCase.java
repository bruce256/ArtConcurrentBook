package chapter05;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 10-20
 */
public class ConditionUseCase {
    Lock      lock      = new ReentrantLock();
    Condition condition = lock.newCondition();

    public static void main(String[] args) {
	    final ConditionUseCase cuc = new ConditionUseCase();

        new Thread(new Runnable() {
	        @Override
	        public void run() {
		        try {
			        Thread.sleep(3 * 1000);
		        } catch (InterruptedException e) {
			        e.printStackTrace();
		        }
		        cuc.conditionSignal();
	        }
        }).start();

	    cuc.conditionWait();
    }
    public void conditionWait() {
        lock.lock();
        try {
	        System.out.println("waite for signal");
            condition.await();
        } catch (InterruptedException e){
	        e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() {
		    lock.lock();
        try {
            condition.signal();
        } catch (Exception e) {
	        e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
