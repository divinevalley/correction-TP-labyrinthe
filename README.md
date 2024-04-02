Elaboré comme correction de TP pour un cours de programmation orientée objet. Il s'agit d'un labyrinthe construit par l'utilisateur par le biais des commandes dans la console. 

En tant qu’utilisateur vous avez 3 types de « commande » possibles en entrée :

Exemple de saisie d'utilisateur : 
1. piece <id> <rencontre>
exemple : piece 1 monstre
2. corridor <id> <id>2
exemple : corridor 0 1
3. CORRIDORS
4. FIN

// entrée de l’utilisateur
piece 1 monstre
piece 2 rien
piece 3 tresor
piece 4 monstre
piece 5 boss
piece 6 tresor
piece 7 monstre
piece 8 rien
CORRIDORS
corridor 1 0
corridor 1 2
corridor 3 2
corridor 3 4
corridor 4 5
corridor 0 5
corridor 0 6
corridor 6 7
corridor 7 8
corridor 4 8
FIN
// sortie du programme
Rapport:
Donjon avec 9 pieces :
<0-RIEN> : [<1-MONSTRE>, <5-BOSS>, <6-TRESOR>]
<1-MONSTRE> : [<0-RIEN>, <2-RIEN>]
<2-RIEN> : [<1-MONSTRE>, <3-TRESOR>]
<3-TRESOR> : [<2-RIEN>, <4-MONSTRE>]
<4-MONSTRE> : [<3-TRESOR>, <5-BOSS>, <8-RIEN>]
// le reste du donjon…..
Non pacifique.
Contient un boss.
Contient 2 tresors.
Chemin jusqu’au boss :
<0-RIEN>
<1-MONSTRE>
<2-RIEN>
<3-TRESOR>
<4-MONSTRE>
<5-BOSS
