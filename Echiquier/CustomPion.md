# Création d'un pion custom

## Introduction
Toutes les classes permettant de gérer des pions sont appelées "Manager".  En effet, cette classe va "manager" les pions pour les positionner correctement.

## Création de la class
Pour implémenter son propre pion, il faut créer une class qui devra hérité soit de `PionManager`, soit de `OpaquePionManager`.  La différence entre les deux se situe simplement dans le fait qu'un pion "opaque" ne peut pas traverser les autres pions.  Par exemple une tour est un pion opaque contrairement au cavalier.

La classe qui permettra de gérer ce nouveau pion devra définir une méthode et un constructeur.  Pour les class opaque, une méthode en plus devra être intégré.


**Canvas:**                           
Le canvas de base pour créer une class est le suivant:           
```java        
public class TestManager extends OpaquePionManager {

    public TestManager(NbrPions nbrPion, int tailleEchec) {
        super("nomPion", nbrPion, tailleEchec, 'S', '8');
    }

    @Override
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, int currentColonne) {
        // Code
    }

    /**
     * Uniquement pour les cases opaques
     */
    @Override
    protected ArrayList<Integer[]> getEmptyCase(int currentLigne, int currentColonne, int currentDecalageLigne, 
        int currentDecalageColonne) {
        // Code
    }

}
```

Le constructeur doit appeller le constructeur parent en précisant le nom du pion (le paramètre `"nomPion"`) (uniquement utilisé pour du débug) ainsi que le symbole qui sera utilisé pour les résultats lors de l'affichage classique ou UTF-8 (respectivement `'S'` et `'8'` dans cet exemple).

La méthode `getAccessibleCase` permet de spécifier toutes les cases accèssibles par ce pion depuis les coordonnées passées en paramètre, à savoir `(currentLigne, currentColonne)`.  Cette méthode retourne donc une liste de coordonnées relatives aux coordonnées passées en paramètres.

La méthode `getEmptyCase` ne doit être définie que pour les pions "opaques".  Cette méthode prend 4 paramètres en entré qui sont respectivement: la position sur la ligne du pion que l'on observe, la position sur la colonne du pion que l'on observe, le déplacement du pion sur la ligne et le déplacement du pion sur la colonne.  En d'autres mots, ces paramètres peuvent être lu deux par deux.  Les deux premiers définissent les coordonnéees du pion actuel : `(currentLigne, currentColonne)`.  Les deux suivant définissent le potentiel déplacement du pion: `(currentDecalageLigne, currentDecalageColonne)`.  La méthode `getEmptyCase` retourne la liste des cases qui doivent être vides entre `(currentLigne, currentColonne)` et `(currentDecalageLigne, currentDecalageColonne)` pour pouvoir être déplacée.

## Déployer
Une fois la classe crée, il indique au programme que celle-ci existe.  Pour cela, il suffit de modifier la classe "PionManager" et d'instancier la classe qui vient d'être crée dans la méthode `initAllManager`.

## Lecture des inputs
Par défaut, le programme ne saura pas combien de pions il doit placer.  Il lit cette information directement dans les arguments utilisés pour lancer le programme.  Pour la classe que vous venez de créer, rien n'a été implémenté pour permettre de directement définir le nombre de pion qui doivent être placé. Il faut donc soit modifier la classe `ReadingArguments` pour palier à cela soit fixer cette valeur dans le programme (dans `initAllManager` ou directement dans le constructeur du nouveau pion) mais il faudra alors recompiler le projet à chaque modification de cette valeur.

