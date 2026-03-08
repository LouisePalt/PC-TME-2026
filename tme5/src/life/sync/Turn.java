package life.sync;

public class Turn {
    private boolean isPlayerOneTurn = true;

    public synchronized void startTurn(boolean isPlayerOne) {
        try {
            while (isPlayerOne != isPlayerOneTurn) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void endTurn() {
       isPlayerOneTurn = !isPlayerOneTurn;
       notifyAll();
    }
}
