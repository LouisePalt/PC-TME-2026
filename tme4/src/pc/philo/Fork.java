package pc.philo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
	private Lock lock = new ReentrantLock();
	
	public void acquire () throws InterruptedException {
		lock.lockInterruptibly();
    }
	
	public void release () {
		lock.unlock();
	}
}
