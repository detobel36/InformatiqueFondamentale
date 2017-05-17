package be.ac.ulb.infofonda;

import be.ac.ulb.infofonda.echec.Echec;
import be.ac.ulb.infofonda.echec.TypeProbleme;

/**
 *
 * @author Remy
 */
public class Main {
    
    public static void main(final String[] args) {
        
        ReadingArguments readingArgs = null;
        try {
            readingArgs = new ReadingArguments(args);
        } catch(IllegalArgumentException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
        
        if(readingArgs != null) {
            final int tailleEchec = readingArgs.getTailleEchec();
            final int nbrFou = readingArgs.getNbrFou();
            final int nbrCavalier = readingArgs.getNbrCavalier();
            final int nbrTour = readingArgs.getNbrTour();
            final boolean utf8 = readingArgs.isUtf8();
            // TODO: changer par TypeProbleme
            final boolean isDomination = readingArgs.isProbDomination();
            final boolean isIndependance = readingArgs.isProbIndependance();
            
            if(isIndependance && isDomination) {
                System.out.println("Erreur vous ne pouvez pas demander la "
                        + "résolution des deux problèmes en même temps");
            } else if(isDomination) {
                new Echec(nbrFou, nbrCavalier, nbrTour, tailleEchec, TypeProbleme.DOMINATION, utf8);
            } else if(isIndependance) {
                new Echec(nbrFou, nbrCavalier, nbrTour, tailleEchec, TypeProbleme.INDEPENDANCE, utf8);
            } else {
                System.out.println("Aucun problème n'a été sélectionné");
            }
            
        }
        
//        Exemples.example();
//        Exemples.queenProblem();
        // nbrFou, nbrCavalier, nbrTour
//        new Echec(0, 0, 5, 5);
//        new Echec(0, 0, 5, 5);
//        new Echec(1, 1, 2, 3);
//        new Echec(0, 0, 2, 2);
//        Exemples.modelit(4);
    }
    
}
