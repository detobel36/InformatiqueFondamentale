package be.ac.ulb.infofonda.echiquier;

import be.ac.ulb.infofonda.echiquier.echec.Echec;
import be.ac.ulb.infofonda.echiquier.echec.NbrPions;
import be.ac.ulb.infofonda.echiquier.echec.TypeProbleme;

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
            final NbrPions nbrFou = readingArgs.getNbrFou();
            final NbrPions nbrCavalier = readingArgs.getNbrCavalier();
            final NbrPions nbrTour = readingArgs.getNbrTour();
            final boolean utf8 = readingArgs.isUtf8();
            final boolean viewAll = readingArgs.viewAll();
            final boolean isDebug = readingArgs.isDebug();
            final boolean isTime = readingArgs.isTime();
            final TypeProbleme typeProbleme = readingArgs.getTypeProbleme();
            
            if(typeProbleme != null) {
                new Echec(nbrFou, nbrCavalier, nbrTour, tailleEchec, 
                        typeProbleme, viewAll, isTime, utf8, isDebug);
            } else {
                System.out.println("Aucun problème n'a été sélectionné");
            }
            
        }
        
    }
    
}
