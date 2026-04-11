package pc;

import java.util.concurrent.atomic.AtomicInteger;

public class ActivityMonitor {
    private final AtomicInteger counter;

    public ActivityMonitor() {
        counter = new AtomicInteger(0);
    }

    public int taskStarted() {
        return counter.incrementAndGet();
    }

    public synchronized void taskCompleted() {
        if (counter.decrementAndGet() == 0) {
            notifyAll();
        }
    }

    public synchronized void awaitCompletion() throws InterruptedException {
        while (counter.get() != 0) {
            wait();
        }
    }

}
