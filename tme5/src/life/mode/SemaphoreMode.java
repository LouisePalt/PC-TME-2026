package life.mode;

import life.LifeModel;
import life.sync.SimpleSemaphore;
import life.ui.LifePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreMode implements LifeMode {

    @Override
    public String getName() {
        return "semaphore";
    }

    @Override
    public LifeModel createModel(int rows, int cols) {
        return new LifeModel(rows, cols);
    }

    @Override
    public void startSimulation(LifeModel model, LifePanel panel, AtomicInteger updateDelayMs, AtomicInteger refreshDelayMs,
                                int n) {
        List<SimpleSemaphore> ready = new ArrayList<>();
        SimpleSemaphore done =  new SimpleSemaphore(0);

        int rows = model.getRows();

        for (int i = 0; i < n; i++) {
            ready.add(new SimpleSemaphore(1));
            int startRow = (i * rows) / n;
            int endRow = ((i + 1) * rows) / n;
            Thread updater = new Thread(new Updater(model, startRow, endRow, ready.get(i), done), "updater-" + i);
            updater.start();
        }

        Thread refresher = new Thread(new Refresher(model, refreshDelayMs, panel, ready, done, n), "refresher");
        refresher.start();
    }

    static final class Updater implements Runnable {
        private final LifeModel model;
        private final int startRow;
        private final int endRow;
        private final SimpleSemaphore ready;
        private final SimpleSemaphore done;

        Updater(LifeModel model, int startRow, int endRow, SimpleSemaphore ready, SimpleSemaphore done) {
            this.model = model;
            this.startRow = startRow;
            this.endRow = endRow;
            this.ready = ready;
            this.done = done;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    ready.acquire(1);
                    model.updateNext(startRow, endRow);
                    done.release(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Thread " + Thread.currentThread().getName() + " quitting");
            }
        }
    }

    static final class Refresher implements Runnable {
        private final LifeModel model;
        private final AtomicInteger delayMs;
        private final LifePanel panel;
        private final List<SimpleSemaphore> ready;
        private final SimpleSemaphore done;
        private final int permits;

        Refresher(LifeModel model, AtomicInteger delayMs, LifePanel panel, List<SimpleSemaphore> ready, SimpleSemaphore done, int n) {
            this.model = model;
            this.delayMs = delayMs;
            this.panel = panel;
            this.ready = ready;
            this.done = done;
            this.permits = n;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    done.acquire(permits);
                    model.refreshCurrent();
                    panel.repaint();
                    for (SimpleSemaphore s : ready) {
                        s.release(1);
                    }
                    int d = delayMs.get();
                    if (d > 0) {
                        Thread.sleep(d);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Thread " + Thread.currentThread().getName() + " quitting");
            }
        }
    }
}
