package be.ac.ulb.infofonda.surveillance.utils;

/**
 * Représente les différentes directions possibles
 * 
 * @author Detobel
 */
public enum Direction {
    
    NORD('N'),
    EST('E'),
    OUEST('O'), 
    SUD('S');
    
    private final char _symbole;
    
    private Direction(final char symbole) {
        _symbole = symbole;
    }
    
    public char getSymbole() {
        return _symbole;
    }
    
}
