package be.ac.ulb.infofonda.echec.pionmanager;

import be.ac.ulb.infofonda.echec.NbrPions;
import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class TourManager extends OpaquePionManager {
    
    private static final Boolean DEBUG_DEPLACEMENT = false;
    
    public TourManager(final NbrPions nbrTour, final int tailleEchec) {
        super("Tour", nbrTour, tailleEchec, 'T', '♜');
    }

    @Override
    public ArrayList<Integer[]> getAccessibleCase(final int currentLigne, 
            final int currentColonne) {
        
        final ArrayList<Integer[]> res = new ArrayList<>();
        for(int decalageLigne = -currentLigne; currentLigne + decalageLigne < _tailleEchec; ++decalageLigne) {
            final int newLigne = currentLigne + decalageLigne;
            if(newLigne != currentLigne) {
                res.add(getCoord(newLigne, currentColonne));
            }
        }
        
        for(int decalageCol = -currentColonne; currentColonne + decalageCol < _tailleEchec; ++decalageCol) {
            final int newCol = currentColonne + decalageCol;
            if(newCol != currentColonne) {
                res.add(getCoord(currentLigne, newCol));
            }
        }
        
        if(DEBUG_DEPLACEMENT) {
            printDebugRestult(res, currentLigne, currentColonne);
        }
        
        
        return res;
    }

    @Override
    protected ArrayList<Integer[]> getEmptyCase(final int currentLigne, 
            final int currentColonne, final int currentDecalageLigne, 
            final int currentDecalageColonne) {
        final ArrayList<Integer[]> result = new ArrayList<>();
        
        // Si on bouge uniquement de colonne
        if(currentLigne == currentDecalageLigne) {
            final int minCol = Math.min(currentColonne, currentDecalageColonne);
            final int maxCol = Math.max(currentColonne, currentDecalageColonne);
            
            for(int col = minCol; col < maxCol; ++col) {
                if(col != currentDecalageColonne) {
                    result.add(getCoord(currentLigne, col));
                }
            }
            
        } else if(currentColonne == currentDecalageColonne) {
            final int minLigne = Math.min(currentLigne, currentDecalageLigne);
            final int maxLigne = Math.max(currentLigne, currentDecalageLigne);
            
            for(int ligne = minLigne; ligne < maxLigne; ++ligne) {
                if(ligne != currentDecalageLigne) {
                    result.add(getCoord(ligne, currentColonne));
                }
            }
            
        } else {
            System.err.println("Error ! Déplacement de la tour invalide (de " + 
                    currentLigne + ", " + currentColonne + " à " + 
                    currentDecalageLigne + ", " + currentDecalageColonne + ")");
        }
        
        return result;
    }
    
}
