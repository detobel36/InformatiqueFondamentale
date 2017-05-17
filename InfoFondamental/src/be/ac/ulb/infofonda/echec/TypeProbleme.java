package be.ac.ulb.infofonda.echec;

import org.chocosolver.solver.Model;

/**
 * Représente le type de problème que l'on veut calculer
 * 
 * @author Remy
 */
public enum TypeProbleme {
    DOMINATION(true, true, Model.MINIMIZE),
    INDEPENDANCE(false, false, Model.MAXIMIZE);
    
    /**
     * Permet de savoir s'il faut faire un OR sur toutes les contraintes ou un AND (si False)
     */
    private final boolean orOperation; 
    
    /**
     * Permet de savoir si le problème actuelle inclus de "l'opacité", c'est à 
     * dire que les cases ne peuvent pas se traverser
     */
    private final boolean haveOpacity; 
    
    /**
     * Permet de savoir si pour optimiser le problème il faut faire un maximum
     * ou un minimum
     */
    private final boolean optimisation;
    
    
    private TypeProbleme(final boolean orOp, final boolean opacity, final boolean opti) {
        orOperation = orOp;
        haveOpacity = opacity;
        optimisation = opti;
    }
    
    public boolean isOrOperation() {
        return orOperation;
    }
    
    public boolean haveOpacity() {
        return haveOpacity;
    }
    
    public boolean getOptimisation() {
        return optimisation;
    }
}
