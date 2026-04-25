package pc.quicksort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class QuickSortTask extends RecursiveAction {
    private static final int THRESHOLD = 900;
    private final int[] array;
    private final int low;
    private final int high;

    public QuickSortTask(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    public static void parSort(int[] array) {
        QuickSortTask quickSortTask = new QuickSortTask(array, 0, array.length - 1);
        try (ForkJoinPool pool = new ForkJoinPool(4)) {
            pool.invoke(quickSortTask);
        }
    }

    @Override
    protected void compute() {
        if (high - low < THRESHOLD) {
            QuickSort.quickSort(array, low, high);
        } else {
            int middle = QuickSort.partition(array, low, high);

            QuickSortTask leftTask = new QuickSortTask(array, low, middle);
            QuickSortTask rightTask = new QuickSortTask(array, middle + 1, high);

            leftTask.fork();
            rightTask.fork();

            rightTask.join();
            leftTask.join();
        }
    }
}
