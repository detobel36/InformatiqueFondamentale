package be.ac.ulb.infofonda.echec;

/**
 *
 * @author Rémy
 */
@Deprecated
public enum PionDeprecated {
    VIDE("", 0),
    CAVALIER("Cavalier", 1),
    TOUR("Tour", 2),
    FOU("Fou", 3);
    
    private final String _nom;
    private final int _valeur;
    
    private PionDeprecated(String nom, int valeur) {
        this._nom = nom;
        this._valeur = valeur;
    }
    
    private String getNom() {
        return this._nom;
    }
    
    ///////// SATIC /////////
    
    public static int[] getAllValues() {
        int[] intValues = new int[PionDeprecated.values().length];
        int i = 0;
        for(PionDeprecated pion : PionDeprecated.values()) {
            intValues[i] = pion._valeur;
            ++i;
        }
        
        return intValues;
    }
    
    public static String getStringValue(int pionValue) {
        String res = "";
        for(PionDeprecated pion : PionDeprecated.values()) {
            if(pion._valeur == pionValue) {
                return pion.getNom();
            }
        }
        return res;
    }
    
}