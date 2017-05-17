package be.ac.ulb.infofonda.echiquier.echec;

/**
 * Représente la manière dont on veut limiter le nombre de pion.  Dans le cas 
 * présent, soit on veut l'optimiser, soit on veut un nombre précis
 * 
 * @author Rémy
 */
public class NbrPions {
    
    private final boolean _notDefine;
    private final boolean _isOptimisation;
    private final int _nombre;
    
    public NbrPions(final int nombre) {
        this(nombre, false, false);
    }
    
    private NbrPions(final int nombre, final boolean isOptimisation, 
            final boolean notDefine) {
        _nombre = nombre;
        _isOptimisation = isOptimisation;
        _notDefine = notDefine;
    }
    
    public boolean isOptimisation() {
        return _isOptimisation;
    }
    
    /**
     * Permet de savoir si le nombre de pion contient un nombre qui est suppérieur
     * ou égal à 0
     * 
     * @return True si le nombre stocké est positif ET que ce n'est pas une
     * optimisation ou un nombre non définit
     */
    public boolean isPositiveNumber() {
        return !isOptimisation() && !isNotDefine() && _nombre >= 0;
    }
    
    public int getNombre() {
        if(isNotDefine() || isOptimisation()) {
            throw new IllegalAccessError("Vous ne pouvez pas appeller cette "
                    + "méthode sur un nombre 'optimisation' ou 'non définit'");
        }
        return _nombre;
    }
    
    public boolean isNotDefine() {
        return _notDefine;
    }
    
    /**
     * Permet de savoir si le nombre de pion est soit optimal, soit un nombre
     * supérieur ou égal à 0 ET qu'il n'est pas non définit
     * 
     * @return True si les critères sont respecté
     */
    public boolean isValide() {
        return !isNotDefine() && (isOptimisation() || getNombre() >= 0);
    }
    
    
    /////////////////////// STATIC ///////////////////////
    
    public static NbrPions getOptimalPion() {
        return new NbrPions(-1, true, false);
    }
    
    /**
     * Permet de récupérer l'objet NbrPions ou le nombre de pion n'est pas définit
     * 
     * @return un objet NbrPions
     */
    public static NbrPions getDefaultPion() {
        return new NbrPions(-1, false, true);
    }
    
}
