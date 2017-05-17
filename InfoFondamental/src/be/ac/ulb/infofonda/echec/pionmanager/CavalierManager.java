package be.ac.ulb.infofonda.echec.pionmanager;

import be.ac.ulb.infofonda.echec.NbrPions;
import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class CavalierManager extends PionManager {
    
    private static final Boolean DEBUG_DEPLACEMENT = false;
    
    public CavalierManager(final NbrPions nbrCavalier, final int tailleEchec) {
        super("Cavalier", nbrCavalier, tailleEchec, 'C', '♞');
    }

    @Override
    public ArrayList<Integer[]> getAccessibleCase(final int currentLigne, 
            final int currentColonne) {
        final ArrayList<Integer[]> allCases = new ArrayList<>();
        
        allCases.add(getCoord(1, 2));
        allCases.add(getCoord(2, 1));
        
        allCases.add(getCoord(-1, 2));
        allCases.add(getCoord(-2, 1));
        
        allCases.add(getCoord(-1, -2));
        allCases.add(getCoord(-2, -1));
        
        allCases.add(getCoord(1, -2));
        allCases.add(getCoord(2, -1));
        
        final ArrayList<Integer[]> result = new ArrayList<>();
        for(final Integer[] coord : allCases) {
            final int newLigne = currentLigne + coord[0];
            final int newCol = currentColonne + coord[1];
            
            if(newLigne >= 0 && newCol >= 0 && newLigne < _tailleEchec && newCol < _tailleEchec) {
                result.add(getCoord(newLigne, newCol));
            }
        }
        
        if(DEBUG_DEPLACEMENT) {
            printDebugRestult(result, currentLigne, currentColonne);
        }
        
        return result;
    }

}
