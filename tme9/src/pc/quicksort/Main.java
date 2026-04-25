package pc.quicksort;

import static pc.quicksort.QuickSort.generateRandomArray;
import static pc.quicksort.QuickSort.quickSort;

public class Main {
    public static void main(String[] args) {
        int SIZE = 1000000;

        int[] arraySeq = generateRandomArray(SIZE);
        int[] arrayPar = arraySeq.clone();

        // Test séquentiel
        long start = System.currentTimeMillis();
        quickSort(arraySeq, 0, arraySeq.length - 1);
        System.out.println("Séquentiel : " + (System.currentTimeMillis() - start) + " ms");

        // Test parallèle
        start = System.currentTimeMillis();
        QuickSortTask.parSort(arrayPar);
        System.out.println("Parallèle  : " + (System.currentTimeMillis() - start) + " ms");
    }
}
