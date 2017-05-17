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
        return new ArrayList<>();
    }
    
    @Override
    protected ArrayList<Integer[]> getEmptyCase(final int ligne, final int col, 
            final int ligneAcc, final int colAcc) {
        throw new UnsupportedOperationException("Vous ne pouvez pas appeller cette méthode "
                + "sur cet objet");
    }
    
    ///////////////////// STATIC /////////////////////
    
    public static Obstacle getInstance() {
        if(_instance == null) {
            _instance = new Obstacle();
        }
        return _instance;
    }
    
}
