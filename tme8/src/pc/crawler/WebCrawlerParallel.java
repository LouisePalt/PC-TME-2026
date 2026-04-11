package pc.crawler;

import pc.ActivityMonitor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.*;
import java.util.AbstractMap.SimpleEntry;

public class WebCrawlerParallel {
    private final BlockingQueue<SimpleEntry<String, Integer>> queue = new LinkedBlockingQueue<>(1000);
    private final int nbThreads;
    private final ExecutorService executor;
    private final Path outputDir;
    private final String baseUrl;
    private final ConcurrentHashMap<String, Boolean> visited = new ConcurrentHashMap<>();
    private final ActivityMonitor activityMonitor;

    public WebCrawlerParallel(int nbThreads, Path outputDir, String baseUrl, ActivityMonitor activityMonitor) {
        this.nbThreads = nbThreads;
        this.executor = Executors.newFixedThreadPool(nbThreads);
        this.outputDir = outputDir;
        this.baseUrl = baseUrl;
        this.activityMonitor = activityMonitor;
        for (int i = 0; i < nbThreads; i++) {
            executor.execute(() -> {
                try {
                    while (true) {
                            SimpleEntry<String, Integer> peer = queue.take();
                            String currentUrl = peer.getKey();
                            if (currentUrl.equals("POISON_PILL")) break;
                        try {
                            Integer depth = peer.getValue();
                            List<String> urls = WebCrawlerUtils.processUrl(currentUrl, baseUrl, outputDir);
                            int newDepth = depth > 0 ? depth-1 : depth;
                            for (String url : urls) {
                                if (newDepth > 0 && visited.putIfAbsent(url, Boolean.TRUE) == null) {
                                    activityMonitor.taskStarted();
                                    queue.put(new SimpleEntry<>(url, newDepth));
                                }
                            }
                        } finally {
                            activityMonitor.taskCompleted();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Exception : impossible d'ouvrir le lien " + e);
                } catch (URISyntaxException e) {
                    System.out.println("Exception : syntaxe de l'URL incorrecte " + e);
                } catch (InterruptedException e) {
                    System.out.println("Exception : interruption du thread " + e);
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    public void crawl(String startUrl, int depth) throws InterruptedException {
        activityMonitor.taskStarted();
        queue.put(new SimpleEntry<>(startUrl, depth));
    }

    public void awaitCompletion() throws InterruptedException {
        activityMonitor.awaitCompletion();
        for (int i = 0; i < nbThreads; i++) {
            queue.put(new SimpleEntry<>("POISON_PILL", 0));
        }
        executor.shutdown();
    }

    public static void main(String[] args) throws Exception {
        String baseUrl = "https://www-licence.ufr-info-p6.jussieu.fr/lmd/licence/2023/ue/LU3IN001-2023oct/index.php";
        Path outputDir = Paths.get("/tmp/crawler/");

        if (!Files.exists(outputDir)) Files.createDirectories(outputDir);

        ActivityMonitor monitor = new ActivityMonitor();
        WebCrawlerParallel crawler = new WebCrawlerParallel(1, outputDir, baseUrl, monitor);
        long start = System.currentTimeMillis();
        crawler.crawl(baseUrl, 2);
        crawler.awaitCompletion();
        long end = System.currentTimeMillis();
        System.out.println("Temps : " + (end - start) + " ms");
    }

}

/*
 nombre de threads : 1, profondeur : 1, mesure : 446ms
 nombre de threads : 2, profondeur : 1, mesure : 621ms
 nombre de threads : 2, profondeur : 2, mesure : 2167ms
 nombre de threads : 4, profondeur : 1, mesure : 610ms
 nombre de threads : 4, profondeur : 2, mesure : 685ms
 nombre de threads : 8, profondeur : 1, mesure : 1075ms
 nombre de threads : 8, profondeur : 2, mesure : 714ms
 nombre de threads : 16, profondeur : 1, mesure : 429ms
 nombre de threads : 16, profondeur : 2, mesure : 726ms
 conclusion : le web crawler étant une tâche I/O-bound et non CPU-bound, le parallélisme aide surtout quand on a beaucoup d'URLS à traiter simultanément.
*/