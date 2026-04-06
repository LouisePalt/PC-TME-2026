# TME 6 - Pool de Thread

## 1.2 Mise en place

### Question 2

- `resolution` contrôle la qualité
- `blockSize` calcule 1 rayons tous les 1/`resolution` pixels (plus il est petit plus l'image est nette)
- `getNormalizedScreenCoordinates` convertit les pixels en coordonnées
- `computePixelInfo(scene, u, v)` calcule la couleur du rayon envoyé
- `gfx.fillRect(x, y, blockSize, blockSize);` peint un carré de `blockSize`x`blockSize`

la double boucle itère sur toutes les colonnes `x` puis sur toutes les lignes `y` et calcule et dessine chaque pixel, l'un après l'autre.

### Question 4

l'image n'est pas propre, on peut observer des artefacts comme des pixels n'ayant pas la bonne couleur.
la variable gfx réalise deux opérations (`gfx.setColor(pixelData.getColor().toAWTColor());`, `gfx.fillRect(x, y, blockSize, blockSize);`) qui ne sont pas atomiques.
supposons deux threads, T1 et T2 :
- T1 set la couleur associée à son pixel
- T2 set la couleur associée à son pixel
- T1 peint le pixel avec la couleur contenu dans gfx (celle de T2)
on est face à un datarace car le pixel détenu par T1 ne va pas être peint de la bonne couleur.

### Question 5

pour une image en full HD e.g. 1920x1080 on a 1 920 * 1 080 = 2 073 600 threads ce qui entraîne :
- une surcharge importante du système
- un coût élevé de création/destruction des threads
- une forte contention sur l'ordonnanceur

### Question 6

#### sans thread :
```shell
Loading skybox Sky.jpg...
Rendered in 2446ms
Skybox ready.
Rendered in 1834ms
Rendered in 2028ms
Rendered in 1743ms
Rendered in 1742ms
Rendered in 1786ms
Rendered in 2072ms
Rendered in 2451ms
Rendered in 2335ms
Rendered in 2561ms
Rendered in 2018ms
```

#### un thread par pixel :
```shell
Loading skybox Sky.jpg...
Rendered in 3069ms
Skybox ready.
Rendered in 2815ms
Rendered in 2114ms
Rendered in 2144ms
Rendered in 2066ms
Rendered in 2155ms
Rendered in 2313ms
Rendered in 2214ms
Rendered in 2333ms
Rendered in 2035ms
Rendered in 2073ms
```

#### un thread par colonne :
```shell
Loading skybox Sky.jpg...
Rendered in 2613ms
Skybox ready.
Rendered in 2031ms
Rendered in 2395ms
Rendered in 1782ms
Rendered in 1770ms
Rendered in 1826ms
Rendered in 2084ms
Rendered in 2043ms
Rendered in 2024ms
Rendered in 2027ms
Rendered in 2113ms
```

#### observation :
d'après les mesures relevées sur 10 rendus après le chargement de la skybox on peut observer les moyennes suivantes pour chaque approche :
- sans thread : moyenne = 2057 ms
- un thread par pixel : moyenne = 2226,2 ms
- un thread par colonne : moyenne = 2009,5 ms

la version **un thread par pixel** est la plus lente, ce qui s’explique par le grand nombre de threads créés,engendrant un coût important de création, d’ordonnancement et de synchronisation.
la version **un thread par colonne** donne les meilleurs résultats car le nombre de threads reste raisonnable, tout en permettant d’exécuter plusieurs calculs en parallèle.
La version **sans thread** reste correcte, mais elle est légèrement moins performante que la version par colonne.

#### conclusion : 
parmi ces trois approches, la version **un thread par colonne** est la plus efficace d'après les mesures effectuées.

## 1.3 Pool de thread "maison"

### Question 7

en plus de `put` et `take`, la classe BlockingQueue propose d’autres méthodes :

- `offer(e)` : ajoute un élément sans bloquer, retourne false si la file est pleine
- `offer(e, timeout, unit)` : attend un certain temps avant d’abandonner
- `add(e)` : ajoute un élément ou lance une exception si la file est pleine

pour la récupération :

- `poll()` : récupère un élément sans bloquer, retourne null si vide
- `poll(timeout, unit)` : attend un certain temps avant de retourner null
- `peek()` : permet de consulter l’élément en tête sans le retirer

## 1.4 Identifier les contentions

### Question 10

même si le problème est embarrassingly parallel, notre implémentation introduit une contention entre les threads.
cette contention vient de l’accès partagé à l’objet Graphics, car les méthodes `setColor` et `fillRect` ne sont pas atomiques, ce qui oblige à les synchroniser avec un bloc synchronized.
ce qui engendre que tous les threads doivent acquérir le même verrou sur gfx pour dessiner, ce qui force une exécution séquentielle des accès à cette ressource.
le problème n’est donc pas propre au calcul vu qu'il est indépendant par pixel, mais au moment de l’affichage qui implique une dépendance entre les threads.

### Question 11

#### threadpool par pixel avec `synchronized` :
```shell
Loading skybox Sky.jpg...
Rendered in 3368ms
Skybox ready.
Rendered in 2705ms
Rendered in 2024ms
Rendered in 2150ms
Rendered in 2461ms
Rendered in 2428ms
Rendered in 2938ms
Rendered in 2566ms
Rendered in 2531ms
Rendered in 2397ms
Rendered in 2386ms
```

#### threadpool par colonne avec `synchronized` :
```shell
Loading skybox Sky.jpg...
Rendered in 2717ms
Skybox ready.
Rendered in 2113ms
Rendered in 2402ms
Rendered in 2126ms
Rendered in 2269ms
Rendered in 2565ms
Rendered in 2529ms
Rendered in 2550ms
Rendered in 2782ms
Rendered in 2779ms
Rendered in 2941ms
```

#### threadpool par pixel sans `synchronized` :
```shell
Loading skybox Sky.jpg...
Rendered in 2870ms
Skybox ready.
Rendered in 2370ms
Rendered in 1937ms
Rendered in 1765ms
Rendered in 1755ms
Rendered in 1773ms
Rendered in 1771ms
Rendered in 1841ms
Rendered in 2052ms
Rendered in 2071ms
Rendered in 2109ms
```

#### threadpool par colonne sans `synchronized` :
```shell
Loading skybox Sky.jpg...
Rendered in 2384ms
Skybox ready.
Rendered in 1862ms
Rendered in 2088ms
Rendered in 1787ms
Rendered in 1821ms
Rendered in 1740ms
Rendered in 1829ms
Rendered in 1748ms
Rendered in 1751ms
Rendered in 1732ms
Rendered in 1786ms
```

#### observation :
les mesures effectuées permettent de comparer l’impact de la synchronisation sur les performances :

1. Pixel + synchronized  
   → ~2000–2900 ms  
   → performances assez faibles
2. Colonne + synchronized  
   → ~2100–2900 ms
   → très proche du cas par pixel

cela montre que l’utilisation de synchronized annule le bénéfice du parallélisme : les threads doivent attendre pour pouvoir accéder à la ressource Graphics, ce qui entraîne une exécution pratiquement séquentielle.

3. Pixel sans synchronized  
   → ~1750–2100 ms  
   → amélioration nette

la suppression du verrou permet aux threads de s’exécuter en parallèle, mais les performances restent limitées à cause du nombre de tâches (une par pixel).

4. Colonne sans synchronized  
   → ~1700–1850 ms  
   → meilleures performances globales

cette approche offre un bon "compromis" : moins de tâches (une par colonne), tout en conservant le parallélisme.

#### conclusion :  
le principal facteur limitant est la synchronisation sur Graphics, qui implique une forte contention, après suppression une granularité trop fine (pixel) dégrade les performances, tandis qu’une granularité plus grande (colonne) permet d’obtenir de meilleurs résultats.