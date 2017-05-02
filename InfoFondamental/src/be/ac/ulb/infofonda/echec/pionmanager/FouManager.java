package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class FouManager extends OpaquePionManager {

    private static final String NOM = "Fou";
    
    
    public FouManager(int nbrFou, int tailleEchec) {
        super(NOM, nbrFou, tailleEchec);
    }
    
    @Override
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, 
            int currentColonne) {
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

    @Override
    protected ArrayList<Integer[]> getEmptyCase(int currentLigne, 
            int currentColonne, int currentDecalageLigne, int currentDecalageColonne) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
