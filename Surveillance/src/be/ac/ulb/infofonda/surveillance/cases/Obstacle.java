package be.ac.ulb.infofonda.surveillance.cases;

import java.util.ArrayList;

/**
 * CaseManager représentant un obstacle
 * 
 * @author Detobel
 */
public class Obstacle extends CaseManager {
    
    private static Obstacle _instance = null;
    
    public Obstacle() {
        super('*', 0, 0, false);
    }
    
    @Override
    protected ArrayList<Integer[]> getAccessibleCase(int ligne, int col) {
        final ArrayList<Integer[]> result = new ArrayList<>();
        result.add(getCoord(ligne, col));
        return result;
    }
    
    @Override
    protected ArrayList<Integer[]> getEmptyCase(final int ligne, final int col, 
            final int ligneAcc, final int colAcc) {
        return new ArrayList<>();
    }
    
    ///////////////////// STATIC /////////////////////
    
    public static Obstacle getInstance() {
        if(_instance == null) {
            _instance = new Obstacle();
        }
        return _instance;
    }
    
}
