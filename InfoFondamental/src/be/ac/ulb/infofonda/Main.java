package be.ac.ulb.infofonda;

import be.ac.ulb.infofonda.echec.Echec;

/**
 *
 * @author Remy
 */
public class Main {
    
    public static void main(String[] args) {
        
        ReadingArguments readingArgs = null;
        try {
            readingArgs = new ReadingArguments(args);
        } catch(IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        if(readingArgs != null && readingArgs.isProbDomination()) {
            int tailleEchec = readingArgs.getTailleEchec();
            int nbrFou = readingArgs.getNbrFou();
            int nbrCavalier = readingArgs.getNbrCavalier();
            int nbrTour = readingArgs.getNbrTour();
            boolean utf8 = readingArgs.isUtf8();
            
            // nbrFou, nbrCavalier, nbrTour
            new Echec(nbrFou, nbrCavalier, nbrTour, tailleEchec, utf8);
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
