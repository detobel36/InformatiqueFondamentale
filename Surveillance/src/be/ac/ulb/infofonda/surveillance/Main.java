package be.ac.ulb.infofonda.surveillance;

import be.ac.ulb.infofonda.surveillance.input.ReadingArguments;
import be.ac.ulb.infofonda.surveillance.input.ReadingFile;
import java.util.ArrayList;

public class Main {
    
    public static void main(final String[] args) {
        
        ReadingArguments readingArgs;
        try {
            readingArgs = new ReadingArguments(args);
            
            final String fichier = readingArgs.getFichier();

            if(fichier != null && !fichier.isEmpty()) {
                final ReadingFile readingFile = new ReadingFile(fichier);
                for(final String ligne : readingFile.getAllContent()) {
                    System.out.println("Oui, ligne: " + ligne);
                }
                
            } else {
                System.err.println("Le fichier indiqué n'est pas valide !");
            }
            
        } catch(IllegalArgumentException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
        
        // TODO: ONLY FOR TEST
        final ArrayList<Integer[]> listeObstacle = new ArrayList<>();
        final int tailleLigne = 6;
        final int tailleCol = 6;
        
        for(int ligne = 0; ligne < tailleLigne; ++ligne) {
            listeObstacle.add(getCoord(ligne, 0));
            listeObstacle.add(getCoord(ligne, tailleCol-1));
        }
        
        for(int col = 1; col < tailleCol-1; ++col) {
            listeObstacle.add(getCoord(0, col));
            listeObstacle.add(getCoord(tailleLigne-1, col));
        }
        
        new Surveillance(6, 6, listeObstacle, true, true);
        
    }
    
    private static Integer[] getCoord(final int ligne, final int col) {
        final Integer[] tempRes = new Integer[2];
        tempRes[0] = ligne;
        tempRes[1] = col;
        return tempRes;
    }
    
}