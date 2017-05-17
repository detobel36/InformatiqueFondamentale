package be.ac.ulb.infofonda.surveillance.cases;

/**
 * Représente une case vide
 * 
 * @author Detobel
 */
public class Vide extends CaseManager {
    
    private static Vide _instance = null;
    
    private Vide() {
        super(' ');
    }
    
    
    ///////////////////// STATIC /////////////////////
    
    public static Vide getInstance() {
        if(_instance == null) {
            _instance = new Vide();
        }
        return _instance;
    }
    
}
