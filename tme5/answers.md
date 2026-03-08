Complétez avec vos réponses.

# Q1

En mode naive, avec les valeurs par défaut des sliders, la simulation semble fonctionner correctement. Après quelques générations, le pattern se stabilise et on observe des structures stables ainsi que quelques oscillateurs.

Quand on modifie les sliders, on peut observer des anomalies visuelles. Si on met le refresh delay très bas ou à 0, l’affichage devient instable : certaines cellules semblent apparaître ou disparaître et la grille peut afficher des états intermédiaires qui ne correspondent pas à une génération.

Si on réduit fortement le update delay, l’évolution interne devient très rapide et l’affichage saute parfois plusieurs générations.

# Q2

En mode naive, deux threads s’exécutent en parallèle :

- le thread updater, qui appelle updateNext() pour calculer la prochaine génération dans la matrice next,
- le thread refresher, qui appelle refreshCurrent() pour copier next vers current puis rafraîchir l’affichage.

Ces deux méthodes ne sont pas synchronisées, ce qui crée une data race sur la matrice next.

Le problème est accentué quand le refresh delay est proche de 0, car le thread refresher tourne presque en continu et copie très souvent next, ce qui augmente la probabilité de lire un état qu'était entrain d'être calculé par updateNext().

# Q5

Avec ```LifeModelSync```, les méthodes du modèle sont maintenant synchronized, donc les accès concurrents au modèle ne peuvent plus se produire en même temps. Les problèmes de tearing qui avaient lieu en mode naive disparaissent car ```updateNext()``` et ```refreshCurrent()``` ne peuvent plus s’exécuter en même temps.

Cependant, le programme n’est pas totalement correct pour le fonctionnement attendu. La synchronisation empêche les data races, mais elle ne garantit pas une alternance régulière entre ```update``` et ```refresh```.

Si on met update delay à 0 et qu’on laisse refresh normal, le thread updater peut exécuter plusieurs ```updateNext()``` d’affilée avant qu’un ```refreshCurrent()``` ne soit exécuté. L’affichage saute alors plusieurs générations.

Inversement, si refresh delay est à 0 et update normal, le thread refresher peut effectuer plusieurs ```refreshCurrent()``` sans qu’un nouvel ```updateNext()``` n’ait été calculé. L’affichage se rafraîchit alors plusieurs fois avec le même état.

Cela s’explique par le fait que synchronized garantit seulement l’exclusion mutuelle, mais ne contrôle pas l’ordre d’exécution des threads. L'ordonnanceur peut toujours laisser un thread prendre le verrou plusieurs fois de suite.

# Q8

Dans les modes précédents, les sleep servaient à ralentir les threads et à essayer de coordonner leur exécution. Dans le mode alternate, cette coordination est maintenant assurée par le mécanisme de synchronisation avec ```wait()``` et ```notifyAll()``` dans ```LifeModelBlock```.

Grâce à ça, l’alternance entre update et refresh est garantie : un thread attend automatiquement que l’autre ait terminé avant de continuer. Il n’est donc plus nécessaire d’utiliser un sleep dans le thread updater pour réguler l’exécution.

En revanche, on peut conserver le sleep dans le thread refresher pour limiter la fréquence de rafraîchissement de l’interface graphique et éviter d’utiliser trop de CPU.

# Q11

En mode multi, plusieurs threads updater sont créés. Chacun calcule la prochaine génération pour une portion différente de la grille. En parallèle, un thread refresher continue d’appeler ```refreshCurrent()``` pour copier next vers current et rafraîchir l’affichage.

Cependant, comme dans le mode naive, il n’y a aucune synchronisation entre ces threads. Les différents updaters écrivent en parallèle dans la matrice next, pendant que le refresher peut copier cette matrice vers current n'importe quand.

Cela provoque des data races : ```refreshCurrent()``` peut lire next alors que certains threads updater sont encore en train d’écrire dedans. On obtient alors une grille partiellement mise à jour.

Avec plusieurs updaters, ces problèmes sont souvent encore plus visibles, car plusieurs threads modifient next simultanément, ce qui augmente la probabilité que ```refreshCurrent()``` lise un état incohérent.