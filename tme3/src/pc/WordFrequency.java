package pc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WordFrequency {

  public static class NaiveWorker implements Runnable {
    private long[] totalWords = new long[1];
    private File file;
    private final long start;
    private final long end;
    Map<String, Integer> map = new HashMap<>();

    public NaiveWorker(File file, long start, long end, Map<String, Integer> map, long[] totalWords) {
      this.file = file;
      this.start = start;
      this.end = end;
      this.map = map;
      this.totalWords = totalWords;
    }

    public void run() {
      try (Scanner scanner = new Scanner(FileUtils.getRange(file, start, end))) {
        while (scanner.hasNext()) {
          String word = cleanWord(scanner.next());
          if (!word.isEmpty()) {
            totalWords[0]++;
            map.compute(word, (w, c) -> c == null ? 1 : c + 1);
          }
        }
      } catch (IOException e) {
        return;
      }
    }
  }

  public static void main(String[] args) throws IOException {
    String filename = args.length > 0 ? args[0] : "data/WarAndPeace.txt";
    String mode = args.length > 1 ? args[1] : "hash";
    int numThreads = args.length > 2 ? Integer.parseInt(args[2]) : 4;

    File file = new File(filename);
    if (!file.exists() || !file.canRead()) {
      System.err.println("Could not open '" + filename + "'. Please provide a readable text file.");
      System.exit(2);
    }

    long fileSize = file.length();
    System.out.println("Preparing to parse " + filename + " (mode=" + mode + ", N=" + numThreads + "), containing "
        + fileSize + " bytes");

    long startTime = System.nanoTime();

    if (mode.equals("hash")) {
      // Sequential full-file processing with hash map
      long totalWords = 0;
      Map<String, Integer> map = new HashMap<>();
      try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNext()) {
          String word = cleanWord(scanner.next());
          if (!word.isEmpty()) {
            totalWords++;
            map.compute(word, (w, c) -> c == null ? 1 : c + 1);
          }
        }
      }
      printResults(totalWords, map);
    } else if (mode.equals("partition")) {
      // Single-threaded, loop over ranges with single map
      long[] parts = FileUtils.partition(file, numThreads);
      long totalWords = 0;
      Map<String, Integer> map = new HashMap<>();

      for (int i = 0; i < numThreads; i++) {
        try (Scanner scanner = new Scanner(FileUtils.getRange(file, parts[i], parts[i + 1]))) {
          while (scanner.hasNext()) {
            String word = cleanWord(scanner.next());
            if (!word.isEmpty()) {
              totalWords++;
              map.compute(word, (w, c) -> c == null ? 1 : c + 1);
            }
          }
        }
      }
      printResults(totalWords, map);
    } else if (mode.equals("naive")) {
      long[] parts = FileUtils.partition(file, numThreads);
      long[] totalWords = new long[1];
      Map<String, Integer> map = new HashMap<>();
      List<Thread> threads = new ArrayList<>();

      for (int i = 0; i < numThreads; i++) {
        threads.add(new Thread(new NaiveWorker(file, parts[i], parts[i+1], map, totalWords)));
      }
      for (Thread t : threads) {
        t.start();
      }
      for (Thread t : threads) {
        try {
          t.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      printResults(totalWords[0], map);
    } else if (mode.equals("naive2")) {
      long[] parts = FileUtils.partition(file, numThreads);
      long[] totalWords = new long[1];
      Map<String, Integer> map = new HashMap<>();
      List<Thread> threads = new ArrayList<>();

      for (int i = 0; i < numThreads; i++) {
        final int start = i;
        threads.add(new Thread(new Runnable() {
          public void run() {
            try (Scanner scanner = new Scanner(FileUtils.getRange(file, parts[start], parts[start+1]))) {
              while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                  totalWords[0]++;
                  map.compute(word, (w, c) -> c == null ? 1 : c + 1);
                }
              }
            } catch (IOException e) {
              return;
            }
        }
        }));
      }
      for (Thread t : threads) {
        t.start();
      }
      for (Thread t : threads) {
        try {
          t.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      printResults(totalWords[0], map);
    } else if (mode.equals("atomic")) {
      long[] parts = FileUtils.partition(file, numThreads);
      AtomicInteger totalWords = new AtomicInteger();
      Map<String, Integer> map = new HashMap<>();
      List<Thread> threads = new ArrayList<>();

      for (int i = 0; i < numThreads; i++) {
        final int start = i;
        threads.add(new Thread(new Runnable() {
          public void run() {
            try (Scanner scanner = new Scanner(FileUtils.getRange(file, parts[start], parts[start+1]))) {
              while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                  totalWords.incrementAndGet();
                  map.compute(word, (w, c) -> c == null ? 1 : c + 1);
                }
              }
            } catch (IOException e) {
              return;
            }
          }
        }));
      }
      for (Thread t : threads) {
        t.start();
      }
      for (Thread t : threads) {
        try {
          t.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      printResults(totalWords.longValue(), map);
    } else if (mode.equals("synchronized")) {
      long[] parts = FileUtils.partition(file, numThreads);
      AtomicInteger totalWords = new AtomicInteger();
      Map<String, Integer> map = new HashMap<>();
      List<Thread> threads = new ArrayList<>();

      for (int i = 0; i < numThreads; i++) {
        final int start = i;
        threads.add(new Thread(new Runnable() {
          public void run() {
            try (Scanner scanner = new Scanner(FileUtils.getRange(file, parts[start], parts[start+1]))) {
              while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                  totalWords.incrementAndGet();
                  synchronized (map) {
                    map.compute(word, (w, c) -> c == null ? 1 : c + 1);
                  }
                }
              }
            } catch (IOException e) {
              return;
            }
          }
        }));
      }
      for (Thread t : threads) {
        t.start();
      }
      for (Thread t : threads) {
        try {
          t.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      printResults(totalWords.longValue(), map);
    } else if (mode.equals("synchronized2")) {
      long[] parts = FileUtils.partition(file, numThreads);
      AtomicInteger totalWords = new AtomicInteger();
      Map<String, Integer> map = new HashMap<>();
      List<Thread> threads = new ArrayList<>();

      for (int i = 0; i < numThreads; i++) {
        final int start = i;
        threads.add(new Thread(new Runnable() {
          public void run() {
            try (Scanner scanner = new Scanner(FileUtils.getRange(file, parts[start], parts[start+1]))) {
              while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                  totalWords.incrementAndGet();
                  synchronized (map) {
                    Integer count = map.get(word);
                    if (count == null) {
                      map.put(word, 1);
                    } else {
                      map.put(word, count + 1);
                    }
                  }
                }
              }
            } catch (IOException e) {
              return;
            }
          }
        }));
      }
      for (Thread t : threads) {
        t.start();
      }
      for (Thread t : threads) {
        try {
          t.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      printResults(totalWords.longValue(), map);
    } else if (mode.equals("lock")) {
      long[] parts = FileUtils.partition(file, numThreads);
      AtomicInteger totalWords = new AtomicInteger();
      Map<String, Integer> map = new HashMap<>();
      List<Thread> threads = new ArrayList<>();
      Lock lock = new ReentrantLock();

      for (int i = 0; i < numThreads; i++) {
        final int start = i;
        threads.add(new Thread(new Runnable() {
          public void run() {
            try (Scanner scanner = new Scanner(FileUtils.getRange(file, parts[start], parts[start+1]))) {
              while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                  totalWords.incrementAndGet();
                  try {
                    lock.lock();
                    map.compute(word, (w, c) -> c == null ? 1 : c + 1);
                  } finally {
                    lock.unlock();
                  }
                }
              }
            } catch (IOException e) {
              return;
            }
          }
        }));
      }
      for (Thread t : threads) {
        t.start();
      }
      for (Thread t : threads) {
        try {
          t.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      printResults(totalWords.longValue(), map);
    } else if (mode.equals("decorated")) {
      long[] parts = FileUtils.partition(file, numThreads);
      AtomicInteger totalWords = new AtomicInteger();
      Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());
      List<Thread> threads = new ArrayList<>();

      for (int i = 0; i < numThreads; i++) {
        final int start = i;
        threads.add(new Thread(new Runnable() {
          public void run() {
            try (Scanner scanner = new Scanner(FileUtils.getRange(file, parts[start], parts[start+1]))) {
              while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                  totalWords.incrementAndGet();
                  map.compute(word, (w, c) -> c == null ? 1 : c + 1);
                }
              }
            } catch (IOException e) {
              return;
            }
          }
        }));
      }
      for (Thread t : threads) {
        t.start();
      }
      for (Thread t : threads) {
        try {
          t.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      printResults(totalWords.longValue(), map);
    } else {
      System.err.println("Unknown mode: " + mode);
      System.exit(1);
    }

    long endTime = System.nanoTime();
    long durationMs = (endTime - startTime) / 1_000_000;
    System.out.println("Total runtime: " + durationMs + " ms for mode " + mode);
  }

  private static void printResults(long totalWords, Map<String, Integer> map) {
    System.out.println("Total words: " + totalWords);
    System.out.println("Unique words: " + map.size());

    List<Map.Entry<String, Integer>> wordList = new ArrayList<>(map.entrySet());
    wordList.sort((e1, e2) -> {
      if (!e1.getValue().equals(e2.getValue())) {
        return Integer.compare(e2.getValue(), e1.getValue()); // desc freq
      } else {
        return e1.getKey().compareTo(e2.getKey()); // asc alpha
      }
    });

    for (Map.Entry<String, Integer> entry : wordList.subList(0, Math.min(5, wordList.size()))) {
      System.out.println(entry.getValue() + " " + entry.getKey());
    }
  }

  private static String cleanWord(String word) {
    return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
  }
}