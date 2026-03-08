package life;

public class LifeModelBlock extends LifeModel {
    private boolean updateTurn = true;

    public LifeModelBlock(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    public synchronized void updateNext(int startRow, int endRow) {
        try {
            while (!updateTurn) {
                wait();
            }
            super.updateNext(startRow, endRow);
            updateTurn = false;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void refreshCurrent() {
        try {
            while (updateTurn) {
                wait();
            }
            super.refreshCurrent();
            updateTurn = true;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
