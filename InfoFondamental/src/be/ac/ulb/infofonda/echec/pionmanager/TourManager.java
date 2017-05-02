package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class TourManager extends OpaquePionManager {
    
    private static final String NOM = "Tour";
    
    public TourManager(int nbrTour, int tailleEchec) {
        super(NOM, nbrTour, tailleEchec);
    }

    @Override
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, int currentColonne) {
        ArrayList<Integer[]> res = new ArrayList<Integer[]>();
        for(int decalageLigne = -currentLigne; currentLigne + decalageLigne < _tailleEchec; ++decalageLigne) {
            res.add(getCoord(currentLigne + decalageLigne, currentColonne));
        }
        
        for(int decalageCol = -currentColonne; currentColonne + decalageCol < _tailleEchec; ++decalageCol) {
            res.add(getCoord(currentLigne, currentColonne + decalageCol));
        }
        
        return res;
    }

    @Override
    protected ArrayList<Integer[]> getEmptyCase(int currentLigne, int currentColonne, 
            int currentDecalageLigne, int currentDecalageColonne) {
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();
        
        // Si on bouge uniquement de colonne
        if(currentLigne == currentDecalageLigne) {
            int minCol = Math.min(currentColonne, currentDecalageColonne);
            int maxCol = Math.max(currentColonne, currentDecalageColonne);
            
            for(int col = minCol; col < maxCol; ++col) {
                result.add(getCoord(currentLigne, col));
            }
            
        } else if(currentColonne == currentDecalageColonne) {
            int minLigne = Math.min(currentLigne, currentDecalageLigne);
            int maxLigne = Math.max(currentLigne, currentDecalageLigne);
            for(int ligne = minLigne; ligne < maxLigne; ++ligne) {
                result.add(getCoord(ligne, currentColonne));
            }
            
        } else {
            System.err.println("Error ! Déplacement de la tour invalide (de " + 
                    currentLigne + ", " + currentColonne + " à " + 
                    currentDecalageLigne + ", " + currentDecalageColonne + ")");
        }
        
        
        return result;
    }
    
}
