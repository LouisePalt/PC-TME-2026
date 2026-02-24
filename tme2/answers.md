Complétez avec vos réponses.

# Q1

### trace de l'exécution 

#### exécution de main("chat chien chat oiseau poisson chien chat lapin tortue souris") :

Début :
totalWords = 0
map = {}

Lecture "chat" → ajouté → {chat=1}
Lecture "chien" → ajouté → {chat=1, chien=1}
Lecture "chat" → incrémenté → {chat=2, chien=1}
Lecture "oiseau" → ajouté → {chat=2, chien=1, oiseau=1}
Lecture "poisson" → ajouté → {chat=2, chien=1, oiseau=1, poisson=1}
Lecture "chien" → incrémenté → {chat=2, chien=2, oiseau=1, poisson=1}
Lecture "chat" → incrémenté → {chat=3, chien=2, oiseau=1, poisson=1}
Lecture "lapin" → ajouté → {chat=3, chien=2, oiseau=1, poisson=1, lapin=1}
Lecture "tortue" → ajouté → {chat=3, chien=2, oiseau=1, poisson=1, lapin=1, tortue=1}
Lecture "souris" → ajouté → {chat=3, chien=2, oiseau=1, poisson=1, lapin=1, tortue=1, souris=1}

Fin :
Total words = 10
Unique words = 7

Après tri :

chat 3
chien 2
lapin 1
oiseau 1
poisson 1

# Q2

### résultat de l'exécution 

#### hash :

Preparing to parse data/WarAndPeace.txt (mode=hash, N=4), containing 3235342 bytes
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 573 ms for mode hash

#### hash2 :

Preparing to parse data/WarAndPeace.txt (mode=hash2, N=4), containing 3235342 bytes
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 581 ms for mode hash2

### comparaison

D'après les résultats obtenus la méthode avec compute est plus efficace que celle avec get et put car compute() fait tout en une seule étape contrairement à get et put qui demandent deux étapes.
En pratique, la différence est faible.

# Q8 

### résultats de l'exécution

#### N = 4 :

Preparing to parse data/WarAndPeace.txt (mode=partition, N=4), containing 3235342 bytes
Computed partition of 3235342 B  into 4 in 1 ms
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 610 ms for mode partition

#### N = 2 :

Preparing to parse data/WarAndPeace.txt (mode=partition, N=2), containing 3235342 bytes
Computed partition of 3235342 B  into 2 in 1 ms
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 622 ms for mode partition

### observation

Le travail total reste identique, seul le mode de lecture change car ce mode reste séquentiel (pas de thread).

# Q11

Cette méthode a comme complexité O(L) avec L étant la taille de la map source.

# Q13

### résultats de l'exécution

#### N = 4 :

Preparing to parse data/WarAndPeace.txt (mode=shard, N=4), containing 3235342 bytes
Computed partition of 3235342 B  into 4 in 4 ms
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 661 ms for mode shard

#### N = 1 :

Preparing to parse data/WarAndPeace.txt (mode=shard, N=1), containing 3235342 bytes
Computed partition of 3235342 B  into 1 in 1 ms
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 898 ms for mode shard

#### N = 2 :

Preparing to parse data/WarAndPeace.txt (mode=shard, N=2), containing 3235342 bytes
Computed partition of 3235342 B  into 2 in 1 ms
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 782 ms for mode shard

#### N = 3 :

Preparing to parse data/WarAndPeace.txt (mode=shard, N=3), containing 3235342 bytes
Computed partition of 3235342 B  into 3 in 2 ms
Total words: 565527
Unique words: 20332
34562 the
22148 and
16709 to
14990 of
10513 a
Total runtime: 856 ms for mode shard

# Q14

D’après les résultats :

N = 1 → 898 ms
N = 2 → 782 ms
N = 3 → 856 ms
N = 4 → 661 ms

La valeur N = 4 semble la plus efficace sur cette machine, car elle donne le temps d’exécution le plus faible.
Ces résultats indiquent que la machine dispose probablement d’environ 4 cœurs physiques.
Le paramètre N correspond au nombre de threads ou de partitions utilisées pour traiter les données en parallèle.
Si N est trop faible, on n’utilise pas toutes les ressources matérielles.
S’il est trop élevé, le surcoût de gestion des threads peut dégrader les performances.