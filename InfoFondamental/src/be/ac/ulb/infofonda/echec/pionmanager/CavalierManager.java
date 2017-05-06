package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class CavalierManager extends PionManager {
    
    private static final String NOM = "Cavalier";
    
    public CavalierManager(int nbrCavalier, int tailleEchec) {
        super(NOM, nbrCavalier, tailleEchec);
    }

    @Override
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, int currentColonne) {
        ArrayList<Integer[]> allCases = new ArrayList<Integer[]>();
        
        allCases.add(getCoord(1, 2));
        allCases.add(getCoord(2, 1));
        
        allCases.add(getCoord(-1, 2));
        allCases.add(getCoord(-2, 1));
        
        allCases.add(getCoord(-1, -2));
        allCases.add(getCoord(-2, -1));
        
        allCases.add(getCoord(1, -2));
        allCases.add(getCoord(2, -1));
        
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();
        for(Integer[] coord : allCases) {
            int newLigne = currentLigne + coord[0];
            int newCol = currentColonne + coord[1];
            
            if(newLigne > 0 && newCol > 0 && newLigne < _tailleEchec && newCol < _tailleEchec) {
                result.add(getCoord(newLigne, newCol));
            }
        }
        
        return result;
    }

}
