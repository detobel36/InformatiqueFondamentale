package be.ac.ulb.infofonda.surveillance;

import java.util.ArrayList;

public class Main {
    
    public static void main(final String[] args) {
        
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
        
        new Surveillance(6, 6, listeObstacle, true);
        
    }
    
    private static Integer[] getCoord(final int ligne, final int col) {
        final Integer[] tempRes = new Integer[2];
        tempRes[0] = ligne;
        tempRes[1] = col;
        return tempRes;
    }
    
}