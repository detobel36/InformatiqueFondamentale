package be.ac.ulb.infofonda.surveillance.utils;

/**
 * Représente les différentes directions possibles
 * 
 * @author Detobel
 */
public enum Direction {
    
    NORD('N', '▴'),
    EST('E', '▸'),
    OUEST('O', '◂'), 
    SUD('S', '▾');
    
    private final char _symbole;
    private final char _utf8Symbole;
    
    private Direction(final char symbole, final char utf8Symbole) {
        _symbole = symbole;
        _utf8Symbole = utf8Symbole;
    }
    
    public char getSymbole() {
        return _symbole;
    }

    /**
     * @return the utf8Symbole
     */
    public char getUtf8Symbole() {
        return _utf8Symbole;
    }
    
}
