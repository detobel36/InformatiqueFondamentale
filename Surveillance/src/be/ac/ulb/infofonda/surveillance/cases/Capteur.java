package be.ac.ulb.infofonda.surveillance.cases;

import be.ac.ulb.infofonda.surveillance.Direction;

/**
 *
 * @author Detobel
 */
public class Capteur extends Case {
    
    private final Direction _direction;
    
    public Capteur(final Direction direction) {
        super(direction.getSymbole());
        _direction = direction;
    }
    
    
    
}
