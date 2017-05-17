package be.ac.ulb.infofonda.surveillance.cases;

import java.util.ArrayList;

/**
 * Représente une case vide
 * 
 * @author Detobel
 */
public class Vide extends CaseManager {
    
    private static Vide _instance = null;
    
    private Vide() {
        super(' ', 0, 0, false);
    }
    
    @Override
    protected ArrayList<Integer[]> getAccessibleCase(int ligne, int col) {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<Integer[]> getEmptyCase(int ligne, int col, int ligneAcc, int colAcc) {
        return new ArrayList<>();
    }
    
    ///////////////////// STATIC /////////////////////
    
    public static Vide getInstance() {
        if(_instance == null) {
            _instance = new Vide();
        }
        return _instance;
    }
    
}
