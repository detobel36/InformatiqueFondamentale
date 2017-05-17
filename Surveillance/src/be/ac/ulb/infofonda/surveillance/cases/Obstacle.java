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
        super('*');
    }
    
    @Override
    protected ArrayList<Integer[]> getAccessibleCase(int ligne, int col) {
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
