# Question 1

### trace de l'éxecution

#### exécution de main("chat chien chat") :

Début :
totalWords = 0
words = []

Lecture "chat" :
cleanWord → "chat"
non vide → totalWords = 1
"chat" pas dans words → ajouté
words = ["chat"]

Lecture "chien" :
cleanWord → "chien"
totalWords = 2
ajout → words = ["chat", "chien"]

Lecture "chat" :
cleanWord → "chat"
totalWords = 3
déjà présent → pas ajouté

Fin :
Total words: 3
Unique words: 2

### complexité :

La complexité en temps de cette opération en fonction du nombre total de mots n et du nombre de mots distincts d est O(n*d) car la recherce d'un mot dans la liste se fait avec contains, qui a un coût linéaire en fonction de d et cette opération est répétée pour les n mots.
Dans le cas où d est proche de n plutot que petit devant n alors cette complexité devient O(n^2).

# Question 2

### trace de l'éxecution

#### exécution de main("chat chien chat oiseau poisson chien chat lapin tortue souris") :

Début :
totalWords = 0
words = []

Lecture "chat" :
cleanWord → "chat"
non vide → totalWords = 1
"chat" pas dans words → ajouté avec compteur 1
words = [("chat",1)]

Lecture "chien" :
cleanWord → "chien"
totalWords = 2
ajout → words = [("chat",1), ("chien",1)]

Lecture "chat" :
cleanWord → "chat"
totalWords = 3
trouvé → compteur incrémenté
words = [("chat",2), ("chien",1)]

Lecture "oiseau" :
cleanWord → "oiseau"
totalWords = 4
ajout → words = [("chat",2), ("chien",1), ("oiseau",1)]

Lecture "poisson" :
cleanWord → "poisson"
totalWords = 5
ajout → words = [("chat",2), ("chien",1), ("oiseau",1), ("poisson",1)]

Lecture "chien" :
cleanWord → "chien"
totalWords = 6
trouvé → compteur incrémenté
words = [("chat",2), ("chien",2), ("oiseau",1), ("poisson",1)]

Lecture "chat" :
cleanWord → "chat"
totalWords = 7
trouvé → compteur incrémenté
words = [("chat",3), ("chien",2), ("oiseau",1), ("poisson",1)]

Lecture "lapin" :
cleanWord → "lapin"
totalWords = 8
ajout → words = [("chat",3), ("chien",2), ("oiseau",1), ("poisson",1), ("lapin",1)]

Lecture "tortue" :
cleanWord → "tortue"
totalWords = 9
ajout → words = [("chat",3), ("chien",2), ("oiseau",1), ("poisson",1), ("lapin",1), ("tortue",1)]

Lecture "souris" :
cleanWord → "souris"
totalWords = 10
ajout → words = [("chat",3), ("chien",2), ("oiseau",1), ("poisson",1), ("lapin",1), ("tortue",1), ("souris",1)]

Fin lecture :
Total words = 10
Unique words = 7

Tri par fréquence décroissante puis alphabétique :

("chat",3)
("chien",2)
("lapin",1)
("oiseau",1)
("poisson",1)
("souris",1)
("tortue",1)

Affichage des 5 mots les plus fréquents :

3 "chat"
2 "chien"
1 "lapin"
1 "oiseau"
1 "poisson"

### complexité :

La recherche d’un mot dans la liste des mots distincts se fait de manière linéaire avec une ArrayList. Pour chaque mot lu (n mots au total), on parcourt jusqu’à d mots distincts et chaque comparaison de chaînes (equals) peut coûter O(L), où L est la longueur maximale d’un mot. La complexité de la boucle principale est donc en O(ndL). Le tri des d mots distincts coûte O(dlogd), avec un comparateur qui peut aussi parcourir les lettres (compareTo), donc O(dlogdL). L’affichage des 5 mots les plus fréquents est en temps constant. Au total, la complexité est donc O(ndL + dlogdL).
Si d est proche de n, on obtient environ O(n^2).

# Question 3

### trace de l'éxecution

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

### complexité :

La recherche d’un mot dans la map des mots distincts a un coût de logd. Pour chaque mot lu (n mots au total), on parcourt jusqu’à d mots distincts et chaque comparaison de chaînes peut coûter O(L), où L est la longueur maximale d’un mot. La complexité de la boucle principale est donc en O(nLlogd). Le tri des d mots distincts coûte O(dlogd), avec un comparateur qui peut aussi parcourir les lettres (compareTo), donc O(dlogdL). L’affichage des 5 mots les plus fréquents est en temps constant. Au total, la complexité est donc O(nLlogd + dlogdL).
Si d est proche de n, on obtient environ O(nlogn).

# Question 4

### trace de l'éxecution

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

### complexité :

Avec une HashMap, l’accès moyen (get, put, containsKey) est en temps constant O(1). Le comptage des fréquences coûte en moyenne O(nL) car le calcul du hash parcours les caractères des mots. L’extraction dans une liste coûte O(d), puis le tri des d mots distincts coûte O(dlogdL) à cause du compareTo sur les chaînes. La complexité totale est donc 0(nL+dlogdL)
Si d est proche de n, on obtient environ O(nlogn).