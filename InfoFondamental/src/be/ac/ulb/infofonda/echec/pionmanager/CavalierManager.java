package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class CavalierManager extends PionManager {
    
    private static int globalIndex = 0; // Permet d'identifier chaque cavalier de manière unique
    
    private static String NOM = "Cavalier";
    
    public CavalierManager(int nbrCavalier, int tailleEchec) {
        super(NOM, nbrCavalier, tailleEchec);
    }

    @Override
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, int currentColonne) {
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();
        
        result.add(getCoord(1, 2));
        result.add(getCoord(2, 1));
        
        result.add(getCoord(-1, 2));
        result.add(getCoord(-2, 1));
        
        result.add(getCoord(-1, -2));
        result.add(getCoord(-2, -1));
        
        result.add(getCoord(1, -2));
        result.add(getCoord(2, -1));
        
        return result;
    }

}
