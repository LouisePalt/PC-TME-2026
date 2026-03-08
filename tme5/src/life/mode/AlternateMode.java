package life.mode;

import life.LifeModel;
import life.LifeModelBlock;
import life.ui.LifePanel;

import java.util.concurrent.atomic.AtomicInteger;

public class AlternateMode implements LifeMode {
    @Override
    public String getName() {
        return "alternate";
    }

    @Override
    public LifeModel createModel(int rows, int cols) {
        return new LifeModelBlock(rows, cols);
    }

    @Override
    public void startSimulation(LifeModel model, LifePanel panel, AtomicInteger updateDelayMs, AtomicInteger refreshDelayMs,
                                int workers) {
        Thread updater = new Thread(new Updater(model, 0, model.getRows(), updateDelayMs), "updater");
        updater.start();

        Thread refresher = new Thread(new Refresher(model, refreshDelayMs, panel), "refresher");
        refresher.start();
    }

    static final class Updater implements Runnable {
        private final LifeModel model;
        private final int startRow;
        private final int endRow;
        private final AtomicInteger delayMs;

        Updater(LifeModel model, int startRow, int endRow, AtomicInteger delayMs) {
            this.model = model;
            this.startRow = startRow;
            this.endRow = endRow;
            this.delayMs = delayMs;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    model.updateNext(startRow, endRow);
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

        Refresher(LifeModel model, AtomicInteger delayMs, LifePanel panel) {
            this.model = model;
            this.delayMs = delayMs;
            this.panel = panel;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    model.refreshCurrent();
                    panel.repaint();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Thread " + Thread.currentThread().getName() + " quitting");
            }
        }
    }
}
