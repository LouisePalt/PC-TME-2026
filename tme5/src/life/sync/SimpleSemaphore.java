package life.sync;

public class SimpleSemaphore {
    private int permits;

    public SimpleSemaphore(int permits) {
        this.permits = permits;
    }

    public synchronized void acquire(int n) throws InterruptedException {
        while (permits < n) {
            wait();
        }
        permits -= n;
    }

    public synchronized void release(int n) {
        permits += n;
        notifyAll();
    }
}
