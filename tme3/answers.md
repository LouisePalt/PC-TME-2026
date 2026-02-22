# Q1

### trace de l'exécution 

#### *hash* :
```shell
Preparing to parse data/WarAndPeace.txt (mode=hash, N=4), containing 3235342 bytes
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 534 ms for mode hash
```

#### *partition* :
```shell
Preparing to parse data/WarAndPeace.txt (mode=partition, N=4), containing 3235342 bytes
Computed partition of 3235342 B  into 4 in 3 ms
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 616 ms for mode partition
```

# Q3

### traces de l'exécution

1. ```shell
   Preparing to parse data/WarAndPeace.txt (mode=naive, N=4), containing 3235342 bytes
   Computed partition of 3235342 B  into 4 in 2 ms
   Exception in thread "Thread-1" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-2" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-0" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Total words: 140732
   Unique words: 10533
   8791 the
   5537 and
   4514 of
   4145 to
   2631 a
   Total runtime: 288 ms for mode naive
   ```

2. ```shell
   Preparing to parse data/WarAndPeace.txt (mode=naive, N=4), containing 3235342 bytes
   Computed partition of 3235342 B  into 4 in 1 ms
   Exception in thread "Thread-0" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-3" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-2" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Total words: 142517
   Unique words: 10165
   7549 the
   5826 and
   4326 to
   3276 of
   2688 a
   Total runtime: 335 ms for mode naive
   ```

3. ```shell
   Preparing to parse data/WarAndPeace.txt (mode=naive, N=4), containing 3235342 bytes
   Computed partition of 3235342 B  into 4 in 2 ms
   Exception in thread "Thread-3" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-1" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-2" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-0" java.lang.ClassCastException: class java.util.HashMap$Node cannot be cast to class java.util.HashMap$TreeNode (java.util.HashMap$Node and java.util.HashMap$TreeNode are in module java.base of loader 'bootstrap')
   at java.base/java.util.HashMap$TreeNode.moveRootToFront(HashMap.java:1994)
   at java.base/java.util.HashMap$TreeNode.treeify(HashMap.java:2110)
   at java.base/java.util.HashMap.treeifyBin(HashMap.java:778)
   at java.base/java.util.HashMap.compute(HashMap.java:1340)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Total words: 60573
   Unique words: 8062
   3344 the
   2089 and
   1178 a
   919 to
   855 in
   Total runtime: 245 ms for mode naive
   ```

4. ```shell
   Preparing to parse data/WarAndPeace.txt (mode=naive, N=4), containing 3235342 bytes
   Computed partition of 3235342 B  into 4 in 1 ms
   Exception in thread "Thread-3" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-2" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-0" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$NaiveWorker.run(WordFrequency.java:35)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Total words: 142442
   Unique words: 10161
   7545 the
   5824 and
   4323 to
   3274 of
   2688 a
   Total runtime: 308 ms for mode naive
   ```

### observations

En comparant avec les traces des modes **hash** et **partition**, on constate que dès la première exécution du mode **naive**, les résultats sont erronés.
De plus, en lançant plusieurs fois ce mode, on observe des exceptions de concurrence et des résultats différents à chaque exécution.
Cela vient du fait que plusieurs threads modifient en même temps la *HashMap* partagée qui n'est pas thread-safe.
Aussi, l'incrémentation de *totalWors[0]* n'est pas atomique, ainsi plusieurs threads peuvent lire la même valeur avant de l'incrémenter.
On est donc face à une **data race** : plusieurs threads accèdent aux mêmes variables partagées dont au moins un en écriture, sans mécanisme de synchronisation.

# Q5

### traces de l'exécution

1. ```shell
   Preparing to parse data/WarAndPeace.txt (mode=atomic, N=4), containing 3235342 bytes
   Computed partition of 3235342 B  into 4 in 1 ms
   Exception in thread "Thread-2" Exception in thread "Thread-3" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-0" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Total words: 142432
   Unique words: 10161
   7545 the
   5824 and
   4322 to
   3274 of
   2688 a
   Total runtime: 273 ms for mode atomic
   ```

2. ```shell
   Preparing to parse data/WarAndPeace.txt (mode=atomic, N=4), containing 3235342 bytes
   Computed partition of 3235342 B  into 4 in 2 ms
   Exception in thread "Thread-3" Exception in thread "Thread-2" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-1" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-0" java.lang.ClassCastException: class java.util.HashMap$Node cannot be cast to class java.util.HashMap$TreeNode (java.util.HashMap$Node and java.util.HashMap$TreeNode are in module java.base of loader 'bootstrap')
   at java.base/java.util.HashMap$TreeNode.moveRootToFront(HashMap.java:1994)
   at java.base/java.util.HashMap$TreeNode.treeify(HashMap.java:2110)
   at java.base/java.util.HashMap.treeifyBin(HashMap.java:778)
   at java.base/java.util.HashMap.compute(HashMap.java:1340)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Total words: 13385
   Unique words: 3127
   578 the
   440 and
   395 to
   190 a
   165 her
   Total runtime: 132 ms for mode atomic
   ```

3. ```shell
   Preparing to parse data/WarAndPeace.txt (mode=atomic, N=4), containing 3235342 bytes
   Computed partition of 3235342 B  into 4 in 2 ms
   Exception in thread "Thread-3" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-0" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Exception in thread "Thread-1" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Total words: 141425
   Unique words: 10313
   9710 the
   5513 and
   4280 to
   3970 of
   2443 a
   Total runtime: 296 ms for mode atomic
   ```

4. ```shell
   Preparing to parse data/WarAndPeace.txt (mode=atomic, N=4), containing 3235342 bytes
   Computed partition of 3235342 B  into 4 in 1 ms
   Exception in thread "Thread-1" Exception in thread "Thread-2" Exception in thread "Thread-0" java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   java.util.ConcurrentModificationException
   at java.base/java.util.HashMap.compute(HashMap.java:1325)
   at pc.WordFrequency$2.run(WordFrequency.java:164)
   at java.base/java.lang.Thread.run(Thread.java:1583)
   Total words: 140629
   Unique words: 10526
   8787 the
   5530 and
   4510 of
   4144 to
   2631 a
   Total runtime: 307 ms for mode atomic
   ```

### observations

En mode atomic, le compteur *totalWords* est remplacé par un **AtomicInteger**, ce qui assure que l'incrémentation du nombre total de mots est atomique (donc thread-safe).
Contrairement aux modes **naive/naive2** il n'y a pas de perte d'incréments sur ce compteur.
Par contre, on remarque que les résultats sont toujours erronés et qu'il y a encore des exceptions de concurrence.
Cela s'explique par le fait que la *HashMap* reste partagée sans mécanisme de synchronisation.
Ainsi, même si l'utilisation d'un **AtomicInteger** corrige le **data race** sur *totalWords*, ça ne change rien au **data race** sur la *HashMap* partagées.

# Q6

### traces d'exécution

1. ```shell
    Preparing to parse data/WarAndPeace.txt (mode=synchronized, N=4), containing 3235342 bytes
    Computed partition of 3235342 B  into 4 in 1 ms
    Total words: 565527
    Unique words: 20332
    34562 the
    22148 and
    16709 to
    14990 of
    10513 a
    Total runtime: 420 ms for mode synchronized
    ```


2. ```shell
    Preparing to parse data/WarAndPeace.txt (mode=synchronized, N=4), containing 3235342 bytes
    Computed partition of 3235342 B  into 4 in 2 ms
    Total words: 565527
    Unique words: 20332
    34562 the
    22148 and
    16709 to
    14990 of
    10513 a
    Total runtime: 463 ms for mode synchronized
    ```


3. ```shell
    Preparing to parse data/WarAndPeace.txt (mode=synchronized, N=4), containing 3235342 bytes
    Computed partition of 3235342 B  into 4 in 1 ms
    Total words: 565527
    Unique words: 20332
    34562 the
    22148 and
    16709 to
    14990 of
    10513 a
    Total runtime: 465 ms for mode synchronized
    ```


4. ```shell
    Preparing to parse data/WarAndPeace.txt (mode=synchronized, N=4), containing 3235342 bytes
    Computed partition of 3235342 B  into 4 in 1 ms
    Total words: 565527
    Unique words: 20332
    34562 the
    22148 and
    16709 to
    14990 of
    10513 a
    Total runtime: 422 ms for mode synchronized
    ```
   
### observations

Dans le mode **synchronized**, qui utilise un **AtomicInteger** pour le compteur *totalWord* et un bloc ```synchronized(map)``` pour corriger le data race restant, on remarque que l'on a plus d'exception de concurrence et que les résultats sont corrects.
Ceci est cohérent car il n' a plus de **data race**.

# Q7

Cette version est incorrecte : 
supposons deux threads, **T0** et **T1**.
- **T0** rentre dans le premier bloc ```synchronized(map)```
- **T0** initialise **count** avec ```map.get(word)``` qui est nul
- **T1** rentre dans le premier bloc ```synchronized(map)``` car **T0** ne détient plus *map*
- **T1** initialise **count** avec ```map.get(word)``` qui est toujours nul
- **T0** rentre alors dans ```if (count == null)``` et ajoute *word* à *map* avec un compteur de 1
- **T1** fais la même chose

Ainsi, *map* contient *word* avec un compteur de 1 au lieu d'avoir *word* avec un compteur de 2.

Solution : voir **synchronized2**

# Q9

### traces d'exécution

1. ```shell
    Preparing to parse data/WarAndPeace.txt (mode=decorated, N=4), containing 3235342 bytes
    Computed partition of 3235342 B  into 4 in 1 ms
    Total words: 565527
    Unique words: 20332
    34562 the
    22148 and
    16709 to
    14990 of
    10513 a
    Total runtime: 434 ms for mode decorated
    ```


2. ```shell
    Preparing to parse data/WarAndPeace.txt (mode=decorated, N=4), containing 3235342 bytes
    Computed partition of 3235342 B  into 4 in 2 ms
    Total words: 565527
    Unique words: 20332
    34562 the
    22148 and
    16709 to
    14990 of
    10513 a
    Total runtime: 588 ms for mode decorated
    ```


3. ```shell
    Preparing to parse data/WarAndPeace.txt (mode=decorated, N=4), containing 3235342 bytes
    Computed partition of 3235342 B  into 4 in 1 ms
    Total words: 565527
    Unique words: 20332
    34562 the
    22148 and
    16709 to
    14990 of
    10513 a
    Total runtime: 433 ms for mode decorated
    ```


4. ```shell
    Preparing to parse data/WarAndPeace.txt (mode=decorated, N=4), containing 3235342 bytes
    Computed partition of 3235342 B  into 4 in 1 ms
    Total words: 565527
    Unique words: 20332
    34562 the
    22148 and
    16709 to
    14990 of
    10513 a
    Total runtime: 481 ms for mode decorated
    ```

Dans le mode **decorated**, qui utilise un **AtomicInteger** pour le compteur *totalWord* et *map* qui est decorée avec **Collections.synchronizedMap(new HashMap<>())** pour corriger le data race restant, on remarque que les résultats sont corrects.
Ceci est cohérent car il n' a plus de **data race**.

# Q10

Cette version est incorrecte : 
supposons deux threads, **T0** et **T1**. 
- **T0** initialise **count** avec map.get(word) qui est nul 
- **T1** initialise **count** avec map.get(word) (qui est toujours nul) car **T0** ne détient plus *map* 
- **T0** rentre alors dans if (count == null) et ajoute *word* à *map* avec un compteur de 1 
- **T1** fais la même chose

Ainsi, *map* contient *word* avec un compteur de 1 au lieu d'avoir *word* avec un compteur de 2.

Solution : 
```shell
    synchronized (map) {
      Integer c = map.get(word);
      map.put(word, (c == null) ? 1 : c + 1);
    }
```