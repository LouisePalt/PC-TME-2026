# Q4

L'execution de se programme peut créer un deadlock.
En effet, si tous les philosphes prennent la baguette de gauche alors la beguette qui est à leur droite est tenue par leur voisin.
Ainsi, ils attendent tous indéfiniment.

# Q5

Le philosophe N-1 ne respecte par l'ordre naturel d'acquisition des baguettes car il prend d'abord la baguette N-1 puis la baguette 0.

# Q7

Non, cette approche ne fonctionne pas sur la version avec deadlock car si un thread est bloqué en attente de verrou il ne se réveille pas pour traiter l'interruption.
