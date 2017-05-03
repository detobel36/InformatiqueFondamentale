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
        
        
        return result;
    }

    @Override
    protected ArrayList<Integer[]> getEmptyCase(int currentLigne, 
            int currentColonne, int currentDecalageLigne, int currentDecalageColonne) {
        
        
        
        // TODO
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
