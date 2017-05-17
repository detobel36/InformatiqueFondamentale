package be.ac.ulb.infofonda.surveillance.cases;

/**
 * Case représentant un obstacle
 * 
 * @author Detobel
 */
public class Obstacle extends Case {
    
    private static Obstacle _instance = null;
    
    public Obstacle() {
        super('*');
    }
    
    
    ///////////////////// STATIC /////////////////////
    
    public static Obstacle getInstance() {
        if(_instance == null) {
            _instance = new Obstacle();
        }
        return _instance;
    }
    
}
