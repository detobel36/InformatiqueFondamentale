package be.ac.ulb.infofonda.echec;

/**
 * Représente le type de problème que l'on veut calculer
 * 
 * @author Remy
 */
public enum TypeProbleme {
    DOMINATION(true, true),
    INDEPENDANCE(false, false);
    
    /**
     * Permet de savoir s'il faut faire un OR sur toutes les contraintes ou un AND (si False)
     */
    private final boolean orOperation; 
    
    /**
     * Permet de savoir si le problème actuelle inclus de "l'opacité", c'est à 
     * dire que les cases ne peuvent pas se traverser
     */
    private final boolean haveOpacity; 
    
    
    private TypeProbleme(final boolean orOp, final boolean opacity) {
        orOperation = orOp;
        haveOpacity = opacity;
    }
    
    public boolean isOrOperation() {
        return orOperation;
    }
    
    public boolean haveOpacity() {
        return haveOpacity;
    }
}
