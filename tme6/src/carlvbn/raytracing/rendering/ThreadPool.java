package carlvbn.raytracing.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;

public class ThreadPool {
    private final BlockingQueue<Runnable> queue;
    private final List<Thread> workers;

    public ThreadPool(int qsize, int nbThreads) {
        queue = new ArrayBlockingQueue<>(qsize);
        workers = new ArrayList<>(nbThreads);
        while(workers.size() < nbThreads) {
            Thread t = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable run = queue.take();
                        run.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            workers.add(t);
            t.start();
        }
    }

    public void execute(Runnable r) {
        try {
            queue.put(r);
        } catch (InterruptedException e) {
            throw new RejectedExecutionException("Executor interrupted", e);
        }
    }
}