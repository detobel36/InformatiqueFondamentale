package be.ac.ulb.infofonda.surveillance.cases;

import be.ac.ulb.infofonda.surveillance.Direction;

/**
 *
 * @author Detobel
 */
public class Capteur extends CaseManager {
    
    private final Direction _direction;
    
    public Capteur(final Direction direction) {
        super(direction.getSymbole());
        _direction = direction;
    }
    
    
    
}
